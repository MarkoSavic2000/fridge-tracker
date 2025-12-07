package com.fridge.tracker.boot.configuration.security.rest;

import com.fridge.tracker.boot.configuration.model.CorsConfiguration;
import com.fridge.tracker.boot.configuration.security.token.interfaces.TokenConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import static com.fridge.tracker.boot.configuration.security.rest.RestConfiguration.WHITE_LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestConfigurationTest {
    @Mock
    TokenConverter tokenConverter;

    @Mock
    CorsConfiguration corsConfiguration;

    @Mock
    HttpSecurity httpSecurity;

    @Mock
    OAuth2ResourceServerConfigurer<HttpSecurity> configurer;

    @Mock
    OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer;

    @Mock
    AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl;

    @Mock
    AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry;

    RestConfiguration restConfiguration;

    @BeforeEach
    void initialize() {
        restConfiguration = new RestConfiguration(tokenConverter, corsConfiguration);
    }

    @Test
    void apiFilterChain_filterChainIsBuilt() {
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.headers(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.oauth2ResourceServer(any())).thenReturn(httpSecurity);

        restConfiguration.apiFilterChain(httpSecurity);

        verify(httpSecurity).build();
    }

    @Test
    void authorizeHttpRequests() {
        when(registry.requestMatchers(WHITE_LIST)).thenReturn(authorizedUrl);
        when(authorizedUrl.permitAll()).thenReturn(registry);
        when(registry.anyRequest()).thenReturn(authorizedUrl);

        restConfiguration.authorizeHttpRequests(registry);

        verify(registry).requestMatchers(WHITE_LIST);
        verify(registry).anyRequest();
        verify(authorizedUrl).permitAll();
        verify(authorizedUrl).authenticated();
    }

    @Test
    void oauthResourceServer_configurerCalledOnce() {
        restConfiguration.oauth2ResourceServer(configurer);

        verify(configurer).jwt(any());
    }

    @Test
    void addTokenConverter_jwtConfigurerCalledOnce() {
        restConfiguration.addTokenConverter(jwtConfigurer);

        verify(jwtConfigurer).jwtAuthenticationConverter(any());
    }

}