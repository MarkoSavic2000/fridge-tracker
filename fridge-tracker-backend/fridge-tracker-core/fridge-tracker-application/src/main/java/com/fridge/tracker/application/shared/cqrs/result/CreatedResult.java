package com.fridge.tracker.application.shared.cqrs.result;

/**
 * Result representation of successful creation of resource.
 *
 * @param <T> type of the result value
 */
public class CreatedResult<T> extends Result<T> {

    public CreatedResult(T value) {
        super(true, value);
    }
}