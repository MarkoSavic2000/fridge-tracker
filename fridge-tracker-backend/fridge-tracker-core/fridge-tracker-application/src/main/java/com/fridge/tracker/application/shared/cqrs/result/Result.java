package com.fridge.tracker.application.shared.cqrs.result;

import lombok.Getter;

/**
 * Generic result type.
 *
 * @param <T> type of the wrapped value
 */
@Getter
public class Result<T> {
    private final boolean success;
    private final T value;

    protected Result(boolean success, T value) {
        this.success = success;
        this.value = value;
    }

    protected Result(T value) {
        this.success = true;
        this.value = value;
    }

}
