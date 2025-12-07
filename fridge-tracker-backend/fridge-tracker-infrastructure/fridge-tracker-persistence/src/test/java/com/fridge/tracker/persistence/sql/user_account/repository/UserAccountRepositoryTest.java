package com.fridge.tracker.persistence.sql.user_account.repository;

import com.fridge.tracker.domain.user_account.model.UserAccount;
import com.fridge.tracker.domain.user_account.repository.UserAccountRepository;
import com.fridge.tracker.persistence.sql.user_account.entity.UserAccountEntity;
import com.fridge.tracker.persistence.sql.user_account.mapper.UserAccountEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountRepositoryTest {
    @Mock
    UserAccountJpaRepository jpaRepository;

    @Mock
    UserAccountEntityMapper mapper;

    UserAccountRepository repository;

    @BeforeEach
    void initialize() {
        repository = new UserAccountRepositoryImpl(jpaRepository, mapper);
    }

    @Test
    void save_returnSavedUserAccount() {
        var entity = new UserAccountEntity();
        var expectedResult = new UserAccount();

        when(mapper.map(any(UserAccount.class))).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.map(entity)).thenReturn(expectedResult);

        UserAccount result = repository.save(expectedResult);

        assertEquals(expectedResult, result);
    }

    @Test
    void get_returnUserAccount() {
        var entity = new UserAccountEntity();
        var expectedResult = Optional.of(new UserAccount());

        when(jpaRepository.findByKeycloakId(any())).thenReturn(entity);
        when(mapper.map(entity)).thenReturn(expectedResult.get());

        Optional<UserAccount> result = repository.get("1");

        assertEquals(expectedResult, result);
    }
}