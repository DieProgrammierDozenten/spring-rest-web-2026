package org.iu.vorlesung.asset_management_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/*
 * Mit der @Configuration-Annotation weiß Spring, dass in dieser Klasse Beans (@Bean) enthalten
 * sind, die vom Spring-Container zur Laufzeit erzeugt werden müssen.
 */
@Configuration
/*
 * Die @EnableWebSecurity-Annotation aktiviert die Web-Security (in der Standardkonfiguration),
 * registriert die Spring-Security-Filter-Chain und ermöglicht uns damit die Anpassung der
 * Authentifizierungs- und Autorisierungslogik.
 */
@EnableWebSecurity
public class SecurityConfig {
    /*
     * In der SecurityFilterChain-Bean definieren wir, für welche HTTP-Requests wir eine
     * Authentifizierung voraussetzen. Außerdem können wir u. a. hier die Art der
     * Authentifizierung (hier HTTP-Basic-Authentifizierung) spezifizieren.
     * Weitere Informationen:
     * https://docs.spring.io/spring-security/reference/servlet/configuration/java.html
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        /*
         * Zur Vereinfachung deaktivieren wir Cross-Site Request Forgery (CSRF).
         */
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                // Frames in H2-Konsole erlauben
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    /*
     * Die UserDetailsService ermöglicht uns die Definition unserer "Benutzerquelle",
     * gegen die bei einem Loginversuch überprüft wird, ob der Nutzer existiert und
     * authentifiziert werden kann.
     *
     * WICHTIG: In einem Produktivsystem würde man weder HTTP-Authentifizierung (Basic)
     * nutzen noch Zugangsdaten im Quellcode aufführen!
     *
     * Weitere Informationen:
     * https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("asset-admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
