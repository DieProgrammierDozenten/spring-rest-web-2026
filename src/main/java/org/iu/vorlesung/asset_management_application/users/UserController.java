package org.iu.vorlesung.asset_management_application.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*
     * Die Ergänzung "{id}" definiert eine Pfadvariable. Diese wird beim Aufruf des
     * Endpunkts automatisch von Spring als Parameter "id" an die Methode übergeben
     * (siehe Annotation @PathVariable). Somit wird bei einem Aufruf von "/users/2"
     * die Methode "getUserById()" mit dem Parameter id = 2 aufgerufen.
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /*
     * Das @PostMapping funktioniert analog zum @GetMapping, allerdings wird die
     * Methode nur bei HTTP-POST-Aufrufen ausgeführt.
     */
    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /*
     * Die Annotation @DeleteMapping funktioniert analog zum @GetMapping, allerdings
     * wird die Methode nur bei HTTP-DELETE-Aufrufen ausgeführt.
     */
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
