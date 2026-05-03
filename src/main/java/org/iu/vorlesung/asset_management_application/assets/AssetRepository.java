package org.iu.vorlesung.asset_management_application.assets;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AssetRepository extends Repository<Asset, Long> {
    Asset save(Asset asset);
    Optional<Asset> findById(Long id);
}
