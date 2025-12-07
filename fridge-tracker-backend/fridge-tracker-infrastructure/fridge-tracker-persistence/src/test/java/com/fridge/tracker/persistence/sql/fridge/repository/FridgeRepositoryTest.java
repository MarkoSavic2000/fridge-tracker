package com.fridge.tracker.persistence.sql.fridge.repository;

import com.fridge.tracker.domain.fridge.model.FridgePage;
import com.fridge.tracker.domain.fridge.model.FridgeQueryFilter;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import com.fridge.tracker.persistence.sql.fridge.entity.FridgeEntity;
import com.fridge.tracker.persistence.sql.fridge.mapper.FridgeEntityMapper;
import com.fridge.tracker.persistence.sql.shared.query.mapper.SortColumnsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FridgeRepositoryTest {
    @Mock
    FridgeJpaRepository jpaRepository;

    @Mock
    FridgeEntityMapper mapper;

    @Mock
    SortColumnsMapper sortColumnsMapper;

    FridgeRepository repository;

    @BeforeEach
    void initialize() {
        repository = new FridgeRepositoryImpl(jpaRepository, mapper, sortColumnsMapper);
    }

    @Test
    void save_jpaRepositoryCalledOnce() {
        var entity = new FridgeEntity();
        var fridge = new FridgeRecord("F");

        when(mapper.map(fridge)).thenReturn(entity);

        repository.save(fridge);

        verify(jpaRepository).save(entity);
    }

    @Test
    void exists_returnTrue() {
        when(jpaRepository.exists(anyString(), anyString())).thenReturn(true);

        boolean result = repository.exists("1", "F1");

        assertTrue(result);
    }

    @Test
    void delete_jpaRepositoryCalledOnce() {
        long fridgeId = 1L;

        repository.delete(fridgeId);

        verify(jpaRepository).deleteById(fridgeId);
    }

    @Test
    void get_returnFridgePage() {
        when(sortColumnsMapper.map(anyList())).thenReturn(Sort.unsorted());
        when(jpaRepository.get(anyString(), any(), any())).thenReturn(Page.empty());

        FridgePage result = repository.get("1", new FridgeQueryFilter(0, 10, List.of(), null));

        assertNotNull(result);
    }
}