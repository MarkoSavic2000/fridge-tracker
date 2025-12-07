package com.fridge.tracker.domain.fridge.model;

import java.time.LocalDateTime;

public interface FridgeDetails {
    Long getId();

    String getName();

    LocalDateTime getCreatedOn();
}
