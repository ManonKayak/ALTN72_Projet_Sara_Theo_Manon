package org.example.altn72_projet_sara_theo_manon.login_security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Swagger totalement public
                        .requestMatchers(
                                "/swagger",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // API ouverte pour tester depuis Swagger
                        .requestMatchers("/api/**").permitAll()
                        // Assets statiques publics
                        .requestMatchers("/css/**","/js/**","/images/**").permitAll()
                        // Le reste nécessite d'être connecté
                        .anyRequest().authenticated()
                )
                // Pas de 403 CSRF quand on fait POST/PUT/DELETE via Swagger
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                // Auth standard (sera utilisée uniquement pour les vues)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
