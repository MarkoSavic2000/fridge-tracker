package com.fridge.tracker.boot.configuration.security.rest;

import com.fridge.tracker.boot.configuration.model.CorsConfiguration;
import com.fridge.tracker.boot.configuration.security.token.interfaces.TokenConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(proxyTargetClass = true)
@RequiredArgsConstructor
public class RestConfiguration {
    static final String[] WHITE_LIST = {
            "/actuator/**"
    };

    private final TokenConverter tokenConverter;
    private final CorsConfiguration corsConfiguration;

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) {
        return http
                .cors(cors -> cors.configurationSource(_ -> corsConfiguration()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(this::authorizeHttpRequests)
                .oauth2ResourceServer(this::oauth2ResourceServer)
                .build();
    }

    protected void authorizeHttpRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry x) {
        x.requestMatchers(WHITE_LIST).permitAll().anyRequest().authenticated();
    }

    protected void addTokenConverter(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer configurer) {
        configurer.jwtAuthenticationConverter(tokenConverter);
    }

    protected void oauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        configurer.jwt(this::addTokenConverter);
    }

    private org.springframework.web.cors.CorsConfiguration corsConfiguration() {
        var config = new org.springframework.web.cors.CorsConfiguration();

        config.setAllowedOrigins(List.of(corsConfiguration.getAllowedOrigins()));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        return config;
    }
}
