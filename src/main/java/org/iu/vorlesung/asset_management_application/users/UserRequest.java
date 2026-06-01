package org.iu.vorlesung.asset_management_application.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Der Name darf nicht leer sein")
        @Size(min = 3, max = 100, message = "Der Name muss zwischen 3 und 100 Zeichen lang sein")
        String name
) {
    /*
     * Dadurch, dass wir Record nutzen, sind Methoden wie toString(), equalsTo() etc. schon direkt definiert.
     * Wir müssen uns also nicht separat darum kümmern.
     */
}
