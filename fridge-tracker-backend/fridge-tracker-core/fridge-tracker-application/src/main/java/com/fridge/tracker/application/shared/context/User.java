package com.fridge.tracker.application.shared.context;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * User's information for the {@link Context}.
 */
@SuppressWarnings("unchecked")
@Getter
public class User {
    private final Object id;
    private final String username;
    private final Set<String> authorities = new HashSet<>();

    public User(Object id, String username, Set<String> authorities) {
        this.id = id;
        this.username = username;
        this.authorities.addAll(authorities);
    }

    public User(Object id, String username) {
        this.id = id;
        this.username = username;
    }

    public <T> T getId() {
        return (T) id;
    }

}