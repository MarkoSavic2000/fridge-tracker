package com.fridge.tracker.application.shared.validation;

/**
 * Interface which every validator should implement.
 *
 * @param <T> type of model which will be validated
 */
public interface Validator<T> {

    /**
     * Validates model.
     *
     * @param model model to validate
     * @return {@link ValidationReport}
     */
    ValidationReport validate(T model);
}