package com.fridge.tracker.boot.configuration.security.token.interfaces;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

/**
 * Converter which takes roles from JSON Web Token and put them into list of {@link SimpleGrantedAuthority}.
 */
public interface AuthoritiesConverter extends Converter<Jwt, List<SimpleGrantedAuthority>> {
}
