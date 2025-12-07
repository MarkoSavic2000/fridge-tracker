package com.fridge.tracker.application.shared.cqrs.result;

/**
 * Result representation of successful update of resource.
 *
 * @param <T> type of the result value
 */
public class UpdatedResult<T> extends Result<T> {

    public UpdatedResult(T value) {
        super(true, value);
    }
}
