package com.fridge.tracker.application.shared.utils;

import com.fridge.tracker.application.shared.validation.Message;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssertionUtils {

    /**
     * Asserts that the given validation report contains an error corresponding to the specified constraint.
     *
     * @param report     validation report to be checked for errors
     * @param constraint constraint to be checked
     */
    public static void assertHasError(ValidationReport report, Message constraint) {
        assertNotValid(report);

        Optional<Message> message = report.getMessages().stream()
                .filter(m -> m.getReference().equals(constraint.getReference()))
                .findFirst();

        assertTrue(message.isPresent());
    }

    /**
     * Asserts that the given validation report is not valid.
     *
     * @param report validation report to be checked for invalidity.
     */
    public static void assertNotValid(ValidationReport report) {
        assertNotNull(report);
        assertTrue(report.isInvalid());
    }

    /**
     * Asserts that the given validation report is valid.
     *
     * @param report validation report to be checked for validity.
     */
    public static void assertValid(ValidationReport report) {
        assertNotNull(report);
        assertTrue(report.isValid());
    }
}
