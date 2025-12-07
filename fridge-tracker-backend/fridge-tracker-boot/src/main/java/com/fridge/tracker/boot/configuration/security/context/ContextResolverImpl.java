package com.fridge.tracker.boot.configuration.security.context;

import com.fridge.tracker.application.shared.context.Context;
import com.fridge.tracker.application.shared.context.MetadataKeys;
import com.fridge.tracker.application.shared.context.User;
import com.fridge.tracker.application.shared.context.interfaces.ContextResolver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class ContextResolverImpl implements ContextResolver {

    @Override
    public Context resolve() {
        JwtAuthenticationToken authentication = getToken();

        Map<String, Object> metadata = createMetadata(authentication);
        Map<String, Object> claims = authentication.getToken().getClaims();
        String id = (String) claims.get("sub");
        String username = (String) claims.get("preferred_username");
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        var user = new User(id, username, authorities);
        return new Context(user, metadata);
    }

    private JwtAuthenticationToken getToken() {
        return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    private Map<String, Object> createMetadata(JwtAuthenticationToken token) {
        var metadata = new HashMap<String, Object>();
        var webDetails = (WebAuthenticationDetails) token.getDetails();

        if (nonNull(webDetails)) {
            metadata.put(MetadataKeys.IP_ADDRESS, webDetails.getRemoteAddress());
        }

        return metadata;
    }
}
