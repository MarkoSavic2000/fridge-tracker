package com.fridge.tracker.boot.configuration.security.token;

import com.fridge.tracker.boot.configuration.security.token.interfaces.AuthoritiesConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
@Component
public class JwtAuthoritiesConverter implements AuthoritiesConverter {

    @Override
    public List<SimpleGrantedAuthority> convert(Jwt jwt) {
        var realmAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());
        var roles = (Collection<String>) realmAccess.getOrDefault("roles", List.of());
        var groups = (Collection<String>) jwt.getClaims().getOrDefault("groups", List.of());
        var scope = Arrays.stream(((String) jwt.getClaims().getOrDefault("scope", "")).split(" ")).toList();

        return Stream.of(roles, groups, scope)
                .flatMap(Collection::stream)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}