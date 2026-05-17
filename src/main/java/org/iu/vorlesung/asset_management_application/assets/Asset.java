package org.iu.vorlesung.asset_management_application.assets;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.iu.vorlesung.asset_management_application.users.User;
import org.jspecify.annotations.NonNull;

@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nonnull
    private String name;

    @Nonnull
    private String type;

    /*
     * Mit @ManyToOne definieren wir die "Gegenrichtung" der 1:n-Beziehung.
     * Da der Fremdschlüssel im Asset definiert ist (jedes Asset gehört genau
     * einem User), definieren wir hier den Spaltennamen ("owner_id") und über
     * den Typ (User) die entsprechende Entity.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Asset() {
    }

    public Asset(@NonNull String name, @NonNull String type) {
        this.name = name;
        this.type = type;
    }

    /*
     * Wir überladen den Constructor, damit wir (optional) auch noch einen Owner für das Asset
     * hinterlegen können.
     */
    public Asset(@NonNull String name, @NonNull String type, User owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
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

    @Nonnull
    public String getType() {
        return type;
    }

    public void setType(@Nonnull String type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
