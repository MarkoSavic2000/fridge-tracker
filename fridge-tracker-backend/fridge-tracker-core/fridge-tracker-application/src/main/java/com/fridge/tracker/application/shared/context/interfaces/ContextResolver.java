package com.fridge.tracker.application.shared.context.interfaces;

import com.fridge.tracker.application.shared.context.Context;

public interface ContextResolver {

    /**
     * Gets the execution context for the current request.
     *
     * @return current execution context
     */
    Context resolve();
}
