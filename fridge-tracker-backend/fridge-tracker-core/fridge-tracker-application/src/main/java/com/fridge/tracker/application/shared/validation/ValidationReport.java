package com.fridge.tracker.application.shared.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * Validation report for business object models and business logic operations.
 * Contains information about validation messages.
 */
@Getter
public class ValidationReport {
    private List<Message> messages = new ArrayList<>();

    public ValidationReport() {
    }

    public ValidationReport(ValidationReport report) {
        this.messages = new ArrayList<>(report.messages);
    }

    public ValidationReport(List<Message> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public ValidationReport(Message message) {
        this.messages = new ArrayList<>(List.of(message));
    }

    public void add(Message message) {
        this.messages.add(message);
    }

    public void add(List<Message> messages) {
        this.messages.addAll(messages);
    }

    public void add(boolean conditionalResult, Message message) {
        if (conditionalResult) {
            add(message);
        }
    }

    public void ifValidAdd(BooleanSupplier constraintToRun, Message message) {
        if (isValid()) {
            add(constraintToRun.getAsBoolean(), message);
        }
    }

    public ValidationReport rule(boolean conditionResult, Message message) {
        add(conditionResult, message);
        return this;
    }

    public ValidationReport ifValidRule(BooleanSupplier constraintToRun, Message message) {
        if (isValid()) {
            add(constraintToRun.getAsBoolean(), message);
        }

        return this;
    }

    public ValidationReport rule(BooleanSupplier constraintToRun, Message message) {
        add(constraintToRun.getAsBoolean(), message);
        return this;
    }

    public boolean isValid() {
        return this.messages.isEmpty();
    }

    public boolean isInvalid() {
        return !isValid();
    }
}
