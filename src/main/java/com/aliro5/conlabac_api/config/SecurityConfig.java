package com.aliro5.conlabac_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivamos CSRF (Cross-Site Request Forgery) para permitir POST desde la WEB
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Desactivamos CORS para evitar bloqueos entre localhost:8081 y 8080
                .cors(AbstractHttpConfigurer::disable)

                // 3. Permitimos todas las peticiones sin autenticación básica de Spring
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 4. Desactivamos el formulario de login por defecto de Spring Security
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 5. Permitimos frames si en algún momento usas la consola H2
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    /**
     * Motor de encriptación para las contraseñas de los usuarios.
     * Se usa para comparar la clave introducida con la de la BD (tipo Z, U, etc.)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}