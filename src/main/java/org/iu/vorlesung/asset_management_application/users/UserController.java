package org.iu.vorlesung.asset_management_application.users;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.iu.vorlesung.asset_management_application.assets.Asset;
import org.iu.vorlesung.asset_management_application.assets.AssetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Über diesen Controller können unsere User-Datenbankeinträge abgefragt und modifiziert werden.
 */
@RestController
/*
 *  Über das @RequestMapping können wir für alle Endpunkte, die in diesem Controller definiert sind,
 *  mit einem Präfix (in diesem Fall "/users") versehen.
 */
@RequestMapping("/users")
public class UserController {
    /*
     * @Autowired bedeutet, dass Spring sich automatisch darum kümmert, ein Objekt für uns zu erzeugen,
     * das das Interface "AssetRepository" implementiert. Über das Attribut "assetRepository" können
     * wir dann die im Interface definierten Methoden aufrufen.
     */
    @Autowired
    private UserRepository userRepository;

    /*
     * Die Annotation @GetMapping definiert eine Methode, die beim Aufruf des
     * Endpunkts "/" (leerer String) mit einem HTTP-GET aufgerufen werden soll.
     */
    @GetMapping("")
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();
        for (User user : users) {
            response.add(toResponse(user));
        }
        return response;
    }

    /*
     * Die Ergänzung "{id}" definiert eine Pfadvariable. Diese wird beim Aufruf des
     * Endpunkts automatisch von Spring als Parameter "id" an die Methode übergeben
     * (siehe Annotation @PathVariable). Somit wird bei einem Aufruf von "/users/2"
     * die Methode "getUserById()" mit dem Parameter id = 2 aufgerufen.
     */
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User mit ID " + id + " nicht gefunden"));
        return toResponse(user);
    }

    /*
     * Das @PostMapping funktioniert analog zum @GetMapping, allerdings wird die
     * Methode nur bei HTTP-POST-Aufrufen ausgeführt.
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        User user = new User(request.name());
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    /*
     * Die Annotation @DeleteMapping funktioniert analog zum @GetMapping, allerdings
     * wird die Methode nur bei HTTP-DELETE-Aufrufen ausgeführt.
     */
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        List<AssetResponse> assetResponses = new ArrayList<>();
        for (Asset asset : user.getAssets()) {
            assetResponses.add(new AssetResponse(asset.getId(), asset.getName(), asset.getType(), user.getName()));
        }
        return new UserResponse(user.getId(), user.getName(), assetResponses);
    }
}
