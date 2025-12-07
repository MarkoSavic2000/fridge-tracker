package com.fridge.tracker.application.shared.cqrs.result;

/**
 * Result representation of successful deletion of resource.
 *
 * @param <T> type of the result value
 */
public class DeletedResult<T> extends Result<T> {

    public DeletedResult(T value) {
        super(true, value);
    }
}