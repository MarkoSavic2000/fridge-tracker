package com.fridge.tracker.application.shared.validation.exception;

import com.fridge.tracker.application.shared.validation.Message;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Exception thrown when {@link ValidationReport} is invalid.
 */
@Getter
public class ValidationException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Validation errors -> [%s]";

    private final transient List<Message> errors;

    public ValidationException(ValidationReport report) {
        super(String.format(MESSAGE_TEMPLATE, createMessage(report.getMessages())));
        errors = report.getMessages();
    }

    private static String createMessage(List<Message> errors) {
        return errors.stream()
                .map(x -> x.getReference() + ":" + x.getLabel() + " ")
                .collect(Collectors.joining());
    }
}
