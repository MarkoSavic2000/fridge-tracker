package com.fridge.tracker.application.fridge.list;

import com.fridge.tracker.application.shared.cqrs.QueryHandler;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import com.fridge.tracker.domain.fridge.model.FridgePage;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListFridgesHandler implements QueryHandler<ListFridgesQuery, PageResult<FridgeDetails>> {
    private final FridgeRepository repository;

    @Override
    public PageResult<FridgeDetails> execute(ListFridgesQuery query) {
        FridgePage page = repository.get(query.getContext().getId(), query.getFilter());

        return new PageResult<>(
                page.getFridges(),
                page.getPageSize(),
                page.getPageNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isHasNext(),
                page.isHasPrevious()
        );
    }
}
