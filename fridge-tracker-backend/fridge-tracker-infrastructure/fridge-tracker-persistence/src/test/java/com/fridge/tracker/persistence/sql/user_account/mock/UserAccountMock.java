package com.fridge.tracker.persistence.sql.user_account.mock;

import com.fridge.tracker.domain.user_account.model.UserAccount;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserAccountMock {

    public static UserAccount create() {
        var account = new UserAccount();

        account.setId(1L);
        account.setKeycloakId("12");
        account.setEmail("email@email");

        return account;
    }
}
