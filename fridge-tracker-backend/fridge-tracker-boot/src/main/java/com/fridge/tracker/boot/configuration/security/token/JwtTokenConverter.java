package com.fridge.tracker.boot.configuration.security.token;

import com.fridge.tracker.boot.configuration.security.token.interfaces.AuthoritiesConverter;
import com.fridge.tracker.boot.configuration.security.token.interfaces.TokenConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenConverter implements TokenConverter {
    private final AuthoritiesConverter converter;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        List<SimpleGrantedAuthority> authorities = converter.convert(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }
}
