package org.iu.vorlesung.asset_management_application.assets;

import org.iu.vorlesung.asset_management_application.users.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends Repository<Asset, Long> {
    List<Asset> findAll();

    /*
     * "ignoreCase" bedeutet, dass die Groß- und Kleinschreibung des "type"-Parameters egal ist
     */
    List<Asset> findByTypeIgnoreCase(String type);

    Optional<Asset> findById(Long id);

    Asset save(Asset asset);

    void deleteById(long id);
}
