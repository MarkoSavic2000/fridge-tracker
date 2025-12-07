package com.fridge.tracker.application.shared.cqrs;

import com.fridge.tracker.application.shared.context.Context;
import lombok.Getter;

/**
 * Base class for {@link Command} and {@link Query}.
 */
@Getter
public abstract sealed class Action permits Command, Query {
    private Context context;

    protected void setContext(Context context) {
        this.context = context;
    }
}
