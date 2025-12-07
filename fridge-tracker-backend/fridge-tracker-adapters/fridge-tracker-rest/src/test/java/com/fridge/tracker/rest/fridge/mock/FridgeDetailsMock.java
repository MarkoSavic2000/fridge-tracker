package com.fridge.tracker.rest.fridge.mock;

import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class FridgeDetailsMock {

    public static FridgeDetails fridgeDetails = new FridgeDetails() {
        @Override
        public Long getId() {
            return 1L;
        }

        @Override
        public String getName() {
            return "Fridge1";
        }

        @Override
        public LocalDateTime getCreatedOn() {
            return LocalDateTime.of(2022, 2, 2, 2, 2);
        }
    };
}
