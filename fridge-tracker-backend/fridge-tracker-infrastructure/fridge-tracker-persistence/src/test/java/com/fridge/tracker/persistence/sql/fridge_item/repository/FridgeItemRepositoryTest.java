package com.fridge.tracker.persistence.sql.fridge_item.repository;

import com.fridge.tracker.domain.fridge_item.model.FridgeItemPage;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity;
import com.fridge.tracker.persistence.sql.fridge_item.mapper.FridgeItemEntityMapper;
import com.fridge.tracker.persistence.sql.fridge_item.query.FridgeItemQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.fridge.tracker.domain.fridge_item.model.enumeration.FridgeItemCategoryEnum.MEAT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FridgeItemRepositoryTest {
    @Mock
    FridgeItemQuery query;

    @Mock
    FridgeItemJpaRepository jpaRepository;

    @Mock
    FridgeItemEntityMapper mapper;

    FridgeItemRepository repository;

    @BeforeEach
    void initialize() {
        repository = new FridgeItemRepositoryImpl(query, jpaRepository, mapper);
    }

    @Test
    void save_returnId() {
        var entity = mock(FridgeItemEntity.class);
        long expectedId = 1;

        when(entity.getId()).thenReturn(expectedId);
        when(mapper.map(any(FridgeItemRecord.class))).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);

        Long result = repository.save(new FridgeItemRecord());

        assertEquals(expectedId, result);
    }

    @Test
    void delete_jpaRepositoryCalledOnce() {
        long id = 1;

        repository.delete(id);

        verify(jpaRepository).delete(id);
    }

    @Test
    void decreaseQuantity_returnTrue() {
        when(jpaRepository.decreaseQuantity(anyLong(), any())).thenReturn(1);

        boolean result = repository.decreaseQuantity(1L, BigDecimal.TWO);

        assertTrue(result);
    }

    @Test
    void get_returnFridgeItemPage() {
        when(query.get(any(FridgeItemQueryFilter.class)))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(1, 1), 10L));
        when(mapper.map(anyList())).thenReturn(List.of());

        FridgeItemPage result = repository.get(new FridgeItemQueryFilter(1, 2, List.of(), 1L,
                "name", MEAT, LocalDate.of(2022, 5, 5), LocalDate.of(2022, 5, 5),
                LocalDate.of(2022, 5, 5), LocalDate.of(2022, 5, 5), true));

        assertNotNull(result);
    }

    @Test
    void getAvailableFridgeItems_returnItems() {
        List<String> expectedResult = List.of("milk");
        when(jpaRepository.getAvailableFridgeItems(anyLong(), any())).thenReturn(expectedResult);

        List<String> result = repository.getAvailableFridgeItems(1L);

        assertEquals(expectedResult, result);
    }
}