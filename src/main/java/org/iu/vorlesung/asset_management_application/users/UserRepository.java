package org.iu.vorlesung.asset_management_application.users;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/*
 * Im UserRepository, das ein JPA-Repository-Interface erweitert, können als
 * Methodensignaturen Abrufe von der Datenbank definiert werden. Hibernate
 * (der verwendete JPA-Provider) baut für uns im Hintergrund automatisch
 * SQL-Statements und Methodenimplementierungen zusammen.w
 */
public interface UserRepository extends Repository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByName(String name);

    User save(User user);

    void deleteById(long id);
}
