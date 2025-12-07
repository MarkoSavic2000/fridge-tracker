package com.fridge.tracker.persistence.sql.user_account.mapper;

import com.fridge.tracker.domain.user_account.model.UserAccount;
import com.fridge.tracker.persistence.sql.user_account.entity.UserAccountEntity;
import com.fridge.tracker.persistence.sql.user_account.mock.UserAccountEntityMock;
import com.fridge.tracker.persistence.sql.user_account.mock.UserAccountMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountEntityMapperTest {
    UserAccountEntityMapper mapper = new UserAccountEntityMapperImpl();

    @Test
    void map_nullEntityInput_returnNull() {
        UserAccount result = mapper.map((UserAccountEntity) null);

        assertNull(result);
    }

    @Test
    void map_returnUserAccount() {
        var input = UserAccountEntityMock.create();

        UserAccount result = mapper.map(input);

        assertNotNull(result);
        assertEquals(input.getId(), result.getId());
        assertEquals(input.getKeycloakId(), result.getKeycloakId());
        assertEquals(input.getEmail(), result.getEmail());
    }

    @Test
    void map_nullInput_returnNull() {
        UserAccountEntity result = mapper.map((UserAccount) null);

        assertNull(result);
    }

    @Test
    void map_returnUserAccountEntity() {
        var input = UserAccountMock.create();

        UserAccountEntity result = mapper.map(input);

        assertNotNull(result);
        assertEquals(input.getId(), result.getId());
        assertEquals(input.getKeycloakId(), result.getKeycloakId());
        assertEquals(input.getEmail(), result.getEmail());
    }
}