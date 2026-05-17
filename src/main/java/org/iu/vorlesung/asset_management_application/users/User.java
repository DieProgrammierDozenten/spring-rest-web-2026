package org.iu.vorlesung.asset_management_application.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.iu.vorlesung.asset_management_application.assets.Asset;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/*
 * Die @Entity-Annotation definiert diese Klasse als einen Bauplan für eine User-
 * Entität in der Datenbank.
 *
 * Hinweis: Da das Wort "user" in SQL ein reserviertes Schlüsselwort ist, nennen wir
 * die Tabelle mit dem Paramater "name" um in "users".
 */
@Entity(name = "users")
public class User {
    /*
     * Mit @Id wird ein Attribut als Primärschlüssel definiert.
     */
    @Id
    /*
     * Damit wir die Ids nicht selbst verwalten müssen, weisen wir
     * Hibernate an, dass die Id automatisch (durch die Datenbank)
     * generiert werden soll.
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
     * @NotNull bedeutet, dass dieses Feld in der Datenbank niemals
     * NULL sein darf.
     */
    @Nonnull
    private String name;

    /*
     * Mit @OneToMany definieren wir eine 1:n-Beziehung, in diesem Fall zur
     * Entity Asset. Der Fremdschlüssel gehört zur Asset-Tabelle, weshalb
     * wir diesen hier referenzieren (Attribut "owner").
     */
    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Asset> assets = new ArrayList<>();

    /*
     * Aus "Bequemlichkeitsgründen" haben wir einen eigenen Constructor mit
     * Parametern angelegt (siehe unten). Hibernate benötigt aber auch den
     * Default-Constructor (ohne Parameter), den wir hier definieren.
     */
    public User() {
    }

    public User(@NonNull String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
