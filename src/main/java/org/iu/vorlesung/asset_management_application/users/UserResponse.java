package org.iu.vorlesung.asset_management_application.users;

import org.iu.vorlesung.asset_management_application.assets.AssetResponse;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        /*
         * Der Typ heir ist AssetResponse (DTO), nicht das Asset (Model). Dadurch stellen
         * wir sicher, dass in der Asset-Liste auch nur die von uns definierten Werte (bspw.
         * owner_name) zurückgegeben werden.
         */
        List<AssetResponse> assets
) {
}