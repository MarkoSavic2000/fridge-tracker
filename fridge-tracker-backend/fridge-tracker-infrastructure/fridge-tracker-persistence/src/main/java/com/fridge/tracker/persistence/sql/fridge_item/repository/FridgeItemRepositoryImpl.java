package com.fridge.tracker.persistence.sql.fridge_item.repository;

import com.fridge.tracker.domain.fridge_item.model.FridgeItemDetails;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemPage;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity;
import com.fridge.tracker.persistence.sql.fridge_item.mapper.FridgeItemEntityMapper;
import com.fridge.tracker.persistence.sql.fridge_item.query.FridgeItemQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FridgeItemRepositoryImpl implements FridgeItemRepository {
    private final FridgeItemQuery query;
    private final FridgeItemJpaRepository jpaRepository;
    private final FridgeItemEntityMapper mapper;

    @Override
    public Long save(FridgeItemRecord fridgeItem) {
        FridgeItemEntity entity = mapper.map(fridgeItem);
        entity.setModifiedOn(LocalDateTime.now());
        FridgeItemEntity saved = jpaRepository.save(entity);
        return saved.getId();
    }

    @Override
    public void delete(Long id) {
        jpaRepository.delete(id);
    }

    @Override
    public BigDecimal getQuantity(Long id) {
        return jpaRepository.getQuantity(id);
    }

    @Override
    public boolean decreaseQuantity(Long fridgeItemId, BigDecimal amount) {
        return jpaRepository.decreaseQuantity(fridgeItemId, amount) == 1;
    }

    @Override
    public FridgeItemPage get(FridgeItemQueryFilter filter) {
        Page<FridgeItemEntity> page = query.get(filter);
        List<FridgeItemDetails> fridgeItems = mapper.map(page.getContent());

        return new FridgeItemPage(
                filter.getPageNumber(),
                filter.getPageSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious(),
                fridgeItems
        );
    }

    @Override
    public List<String> getAvailableFridgeItems(Long fridgeId) {
        return jpaRepository.getAvailableFridgeItems(fridgeId, LocalDate.now());
    }
}
