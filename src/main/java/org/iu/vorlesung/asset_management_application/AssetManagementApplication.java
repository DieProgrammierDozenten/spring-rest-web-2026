package org.iu.vorlesung.asset_management_application;

import org.iu.vorlesung.asset_management_application.assets.Asset;
import org.iu.vorlesung.asset_management_application.assets.AssetRepository;
import org.iu.vorlesung.asset_management_application.users.User;
import org.iu.vorlesung.asset_management_application.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*
 * Unsere Klasse ist mit der Annotation @SpringBootApplication versehen. Diese
 * weist das Spring-Framework an, diese Klasse beim Start des Projekts auszuführen.
 */
@SpringBootApplication
public class AssetManagementApplication {

    /*
     * Die main()-Method wird beim Start der Applikation ausgeführt.
     * Hier startet sie eine Spring-Applikation mit unserem Code.
     */
    public static void main(String[] args) {
        SpringApplication.run(AssetManagementApplication.class, args);
    }

    /*
     * Dieser Code wird nach dem Start der Anwendung ausgeführt und dient
     * uns als "Spielplatz" zum Ausprobieren und zur Initialbefüllung der
     * Datenbank.
     */
    @Bean
    CommandLineRunner runner(AssetRepository assetRepository, UserRepository userRepository) {
        return args -> {
            User user1 = new User("Max");
            User user2 = new User("Erika");

            // Die Benutzer müssen in der Datenbank gespeichert sein, sonst können sie nicht als Owner referenziert werden
            userRepository.save(user1);
            userRepository.save(user2);

            Asset asset1 = new Asset("MacBook", "Laptop", user2);
            Asset asset2 = new Asset("Büroschlüssel", "Schlüssel");

            assetRepository.save(asset1);
            assetRepository.save(asset2);
        };
    }
}
