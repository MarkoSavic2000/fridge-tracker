package com.fridge.tracker.persistence.sql.user_account.mock;

import com.fridge.tracker.persistence.sql.user_account.entity.UserAccountEntity;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserAccountEntityMock {

    public static UserAccountEntity create() {
        var entity = new UserAccountEntity();

        entity.setId(1L);
        entity.setKeycloakId("12");
        entity.setEmail("email@email");

        return entity;
    }
}
