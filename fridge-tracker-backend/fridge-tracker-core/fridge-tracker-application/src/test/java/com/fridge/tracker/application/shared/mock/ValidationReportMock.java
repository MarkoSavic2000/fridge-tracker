package com.fridge.tracker.application.shared.mock;

import com.fridge.tracker.application.shared.validation.Message;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ValidationReportMock {
    public static ValidationReport validReport = new ValidationReport();
    public static ValidationReport invalidReport = new ValidationReport(List.of(new Message("id", "invalid")));
}
