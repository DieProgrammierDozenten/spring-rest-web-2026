package org.iu.vorlesung.asset_management_application.assets;

public record AssetResponse(
        Long id,
        String name,
        String type,
        /*
         * Wir geben nicht alle Felder zurück, sondern nur die
         * für unseren Client (unser Frontend) relevanten.
         */
        String ownerName
) {
}