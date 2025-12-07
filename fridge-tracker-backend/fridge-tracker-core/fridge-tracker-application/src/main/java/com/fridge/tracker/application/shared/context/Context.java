package com.fridge.tracker.application.shared.context;

import com.fridge.tracker.application.shared.context.exception.MetadataNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about the user who is executing action.
 */
public class Context {
    private final User user;
    private final Map<String, Object> metadata;

    public Context(User user) {
        this.user = user;
        metadata = new HashMap<>();
    }

    public Context(User user, Map<String, Object> metadata) {
        this.user = user;
        this.metadata = metadata;
    }

    /**
     * Gets metadata with the provided key.
     *
     * @param key unique identifier for the metadata
     * @param <R> type of the metadata
     * @return metadata
     */
    @SuppressWarnings("unchecked")
    public <R> R getMetadata(String key) {
        if (!metadata.containsKey(key)) {
            throw new MetadataNotFoundException(key);
        }

        return (R) metadata.get(key);
    }

    /**
     * Gets unique identifier of the user as a {@link String}.
     */
    public String getId() {
        return user.getId().toString();
    }

    /**
     * Gets username of the user.
     *
     * @return username
     */
    public String getUsername() {
        return user.getUsername();
    }
}
