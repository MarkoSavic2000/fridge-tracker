package com.fridge.tracker.persistence.sql.shared.security;

import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.persistence.sql.fridge.repository.FridgeJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FridgeAccessServiceTest {
    @Mock
    FridgeJpaRepository repository;

    FridgeAccessService service;

    @BeforeEach
    void initialize() {
        service = new FridgeAccessServiceImpl(repository);
    }

    @Test
    void hasAccess_returnTrue() {
        when(repository.hasAccess(anyString(), anyLong())).thenReturn(true);

        boolean result = service.hasAccess("12", 2L);

        assertTrue(result);
    }
}