package org.iu.vorlesung.asset_management_application;

import org.iu.vorlesung.asset_management_application.assets.Asset;
import org.iu.vorlesung.asset_management_application.assets.AssetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AssetManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AssetRepository repository) {
        return args -> {
            Asset asset1 = new Asset("MacBook", "Laptop");
            Asset asset2 = new Asset("Büroschlüssel", "Schlüssel");

            repository.save(asset1);
            repository.save(asset2);

            System.out.println(repository.findById(1L));
        };
    }
}
