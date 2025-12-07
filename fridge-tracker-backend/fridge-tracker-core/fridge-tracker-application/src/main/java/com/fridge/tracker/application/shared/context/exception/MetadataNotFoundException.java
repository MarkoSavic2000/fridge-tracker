package com.fridge.tracker.application.shared.context.exception;

import com.fridge.tracker.application.shared.context.Context;

/**
 * Exception thrown when the {@link Context} does not contain metadata with provided key.
 */
public class MetadataNotFoundException extends RuntimeException {

    public MetadataNotFoundException(String metadataKey) {
        super(String.format("Context does not contain metadata with key '%s'", metadataKey));
    }
}
