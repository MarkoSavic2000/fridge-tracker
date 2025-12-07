package com.fridge.tracker.boot.configuration.security.token.interfaces;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Extracts user related data from the JSON Web Token and put it in to {@link AbstractAuthenticationToken}.
 */
public interface TokenConverter extends Converter<Jwt, AbstractAuthenticationToken> {
}
