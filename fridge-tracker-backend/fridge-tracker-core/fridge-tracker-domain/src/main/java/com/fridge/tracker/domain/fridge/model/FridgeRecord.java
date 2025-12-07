package com.fridge.tracker.domain.fridge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FridgeRecord {
    private String name;
    private Long userAccountId;

    public FridgeRecord(String name) {
        this.name = name;
    }
}
