package com.fridge.tracker.persistence.sql.fridge.mock;

import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class FridgeRecordMock {

    public static FridgeRecord create() {
        var fridge = new FridgeRecord("1F");
        fridge.setUserAccountId(12L);

        return fridge;
    }
}
