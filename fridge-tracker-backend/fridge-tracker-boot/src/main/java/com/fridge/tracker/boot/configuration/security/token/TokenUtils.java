package com.fridge.tracker.boot.configuration.security.token;

import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static lombok.AccessLevel.PRIVATE;

/**
 * Class which holds support methods for extraction of token data
 */
@NoArgsConstructor(access = PRIVATE)
public final class TokenUtils {

    /**
     * Gets username of the user who accesses the application
     */
    public static String getUsername() {
        return getUsername(getToken());
    }

    /**
     * Gets username of the user who accesses the application.
     *
     * @param authentication spring's authentication class, which holds data
     */
    public static String getUsername(JwtAuthenticationToken authentication) {
        return (String) authentication.getToken().getClaims().get("preferred_username");
    }

    /**
     * Gets authentication token from @{@link SecurityContextHolder}.
     */
    public static JwtAuthenticationToken getToken() {
        return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }
}
