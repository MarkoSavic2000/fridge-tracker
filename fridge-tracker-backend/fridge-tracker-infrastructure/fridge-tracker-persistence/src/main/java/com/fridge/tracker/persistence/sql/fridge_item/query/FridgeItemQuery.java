package com.fridge.tracker.persistence.sql.fridge_item.query;

import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity;
import org.springframework.data.domain.Page;

public interface FridgeItemQuery {

    /**
     * Gets {@link Page} of {@link FridgeItemEntity} based on the provided filter.
     *
     * @param filter contains filter data
     * @return {@link Page} of {@link FridgeItemEntity}
     */
    Page<FridgeItemEntity> get(FridgeItemQueryFilter filter);
}
