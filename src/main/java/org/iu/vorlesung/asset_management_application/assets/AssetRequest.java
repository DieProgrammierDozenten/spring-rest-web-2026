package org.iu.vorlesung.asset_management_application.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record AssetRequest(
        @NotBlank(message = "Der Name darf nicht leer sein")
        @Size(min = 3, max = 100, message = "Der Name muss zwischen 3 und 100 Zeichen lang sein")
        String name,

        @Pattern(regexp = "Laptop|Smartphone|Schlüssel|Fahrzeug",
                message = "Ungültiger Typ. Erlaubte Werte: Laptop, Smartphone, Schlüssel, Fahrzeug")
        String type,

        /* Durch die @JsonProperty-Annotation werden ownerId und owner_id akzeptiert */
        @JsonProperty("owner_id")
        @Min(value = 1, message = "Die Eigentümer-Id muss größer als 0 sein.")
        Long ownerId
) {
    /*
     * Dadurch, dass wir Record nutzen, sind Methoden wie toString(), equalsTo() etc. schon direkt definiert.
     * Wir müssen uns also nicht separat darum kümmern.
     */
}
