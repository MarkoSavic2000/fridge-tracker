package com.fridge.tracker.application.fridge_item.list;

import com.fridge.tracker.application.shared.cqrs.QueryHandler;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemDetails;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemPage;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListFridgeItemsHandler implements QueryHandler<ListFridgeItemsQuery, PageResult<FridgeItemDetails>> {
    private final FridgeAccessService accessService;
    private final FridgeItemRepository repository;

    @Override
    public PageResult<FridgeItemDetails> execute(ListFridgeItemsQuery query) {
        String userId = query.getContext().getId();
        Long fridgeId = query.getFilter().getFridgeId();

        if (!accessService.hasAccess(userId, fridgeId)) {
            throw new FridgeAccessDeniedException(userId, fridgeId);
        }

        FridgeItemPage page = repository.get(query.getFilter());

        return new PageResult<>(
                page.getFridgeItems(),
                page.getPageSize(),
                page.getPageNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isHasNext(),
                page.isHasPrevious()
        );
    }
}
