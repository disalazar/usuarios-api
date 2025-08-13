package com.smartjob.usuarios_api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // In a PROD environment, ths must be refactorized.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users").permitAll() // Free for "/users (POST)", this is only to simplify access to this exam.
                        .requestMatchers("/h2-console/**").permitAll() // Free for H2 console, this is only to simplify access to this exam.
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Free for Swagger, this is only to simplify access to this exam.
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // Allow iframe for H2 console
                )
                .httpBasic(basic -> {});

        return http.build();
    }
}
