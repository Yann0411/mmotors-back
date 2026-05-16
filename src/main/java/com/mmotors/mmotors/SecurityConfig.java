package com.mmotors.mmotors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS LE SECURITY_CONFIG<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>J'injecte le jwtFilter dans security Config<=<=<=<=<=<=<=<=<=<=<=<=");
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        System.out.println("=>=>=>=>=>=>=>=>=>=>JE SUIS DANS LE SECURITY_CONFIG<=<=<=<=<=<=<=<=<=<=<=<=<<=");
        System.out.println("=>=>=>=>=>=>=>=>=>SECURITY_FILTER_CHAIN <=<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");

        http
                  .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/vehicules/**", "/auth/**", "/actuator/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS LE SECURITY_CONFIG<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>=> CORS_CONFIGURATION_SOURCE<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

