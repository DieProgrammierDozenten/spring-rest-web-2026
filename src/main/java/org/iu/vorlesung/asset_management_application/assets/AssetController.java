package org.iu.vorlesung.asset_management_application.assets;

import org.iu.vorlesung.asset_management_application.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<Asset> getAllAssets(@RequestParam(required = false) String type) {
        /*
         * Der optionale @RequestParam required kann beim Aufruf des Endpunkts angehängt (bspw.
         * "/users?type=smartphone") werden, dann werden die Assets nach diesem Typ gefiltert.
         * Ist dieser nicht definiert, werden alle Assets aus der Datenbank ausgegeben.
         */
        if (type == null) {
            return assetRepository.findAll();
        } else {
            return assetRepository.findByTypeIgnoreCase(type);
        }
    }

    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public Asset createAsset(@RequestBody Asset asset) {
        // TODO: Wir behelfen uns hier, indem wir ein JSON-User-Objekt als "owner" vom UI senden. Das ist unschön.
        return assetRepository.save(asset);
    }

    @DeleteMapping("/{id}")
    public void deleteAssetById(@PathVariable Long id) {
        assetRepository.deleteById(id);
    }
}
