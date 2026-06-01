package org.iu.vorlesung.asset_management_application.assets;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.iu.vorlesung.asset_management_application.users.User;
import org.iu.vorlesung.asset_management_application.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<AssetResponse> getAllAssets(@RequestParam(required = false) String type) {
        /*
         * Der optionale @RequestParam required kann beim Aufruf des Endpunkts angehängt (bspw.
         * "/users?type=smartphone") werden, dann werden die Assets nach diesem Typ gefiltert.
         * Ist dieser nicht definiert, werden alle Assets aus der Datenbank ausgegeben.
         */
        List<Asset> assets;
        if (type == null) {
            assets = assetRepository.findAll();
        } else {
            assets = assetRepository.findByTypeIgnoreCase(type);
        }

        List<AssetResponse> response = new ArrayList<>();
        for (Asset asset : assets) {
            String ownerName = asset.getOwner() != null ? asset.getOwner().getName() : null;
            response.add(new AssetResponse(asset.getId(), asset.getName(), asset.getType(), ownerName));
        }
        return response;
    }

    @GetMapping("/{id}")
    public AssetResponse getAssetById(@PathVariable Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asset mit ID " + id + " nicht gefunden"));

        String ownerName = asset.getOwner() != null ? asset.getOwner().getName() : null;
        return new AssetResponse(asset.getId(), asset.getName(), asset.getType(), ownerName);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AssetResponse createAsset(@Valid @RequestBody AssetRequest request) {
        User owner = null;
        if (request.ownerId() != null) {
            owner = userRepository.findById(request.ownerId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "User mit ID " + request.ownerId() + " nicht gefunden"));
        }

        Asset savedAsset = assetRepository.save(new Asset(request.name(), request.type(), owner));

        String ownerName = savedAsset.getOwner() != null ? savedAsset.getOwner().getName() : null;
        return new AssetResponse(savedAsset.getId(), savedAsset.getName(), savedAsset.getType(), ownerName);
    }

    @DeleteMapping("/{id}")
    public void deleteAssetById(@PathVariable Long id) {
        assetRepository.deleteById(id);
    }
}
