package com.fridge.tracker.boot.configuration.security.context;

import com.fridge.tracker.application.shared.context.Context;
import com.fridge.tracker.application.shared.context.interfaces.ContextResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContextResolverImplTest {
    @Mock
    Jwt jwt;

    @Mock
    JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);

    @Mock
    WebAuthenticationDetails webDetails = mock(WebAuthenticationDetails.class);

    ContextResolver resolver = new ContextResolverImpl();

    @BeforeEach
    void initializeSecurityContextHolder() {
        when(jwt.getClaims()).thenReturn(Map.of("preferred_username", "TEST", "sub", "1"));
        when(token.getToken()).thenReturn(jwt);
        when(token.getAuthorities()).thenReturn(List.of());
        when(token.getDetails()).thenReturn(webDetails);

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Test
    void resolve_returnContext() {
        Context context = resolver.resolve();

        assertNotNull(context);
        assertEquals("1", context.getId());
        assertEquals("TEST", context.getUsername());
    }
}