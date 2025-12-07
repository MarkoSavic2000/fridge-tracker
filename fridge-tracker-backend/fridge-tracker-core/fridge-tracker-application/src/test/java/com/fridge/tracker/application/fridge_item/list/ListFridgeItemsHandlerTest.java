package com.fridge.tracker.application.fridge_item.list;

import com.fridge.tracker.application.shared.cqrs.QueryHandler;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemDetails;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemPage;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.domain.fridge_item.repository.FridgeItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListFridgeItemsHandlerTest {
    @Mock
    FridgeAccessService accessService;

    @Mock
    FridgeItemRepository repository;

    QueryHandler<ListFridgeItemsQuery, PageResult<FridgeItemDetails>> handler;

    @BeforeEach
    void initialize() {
        handler = new ListFridgeItemsHandler(accessService, repository);
    }

    @Test
    void execute_forbiddenAccess_throwFridgeAccessDeniedException() {
        var query = mock(ListFridgeItemsQuery.class);
        var filter = mock(FridgeItemQueryFilter.class);
        long fridgeId = 1L;

        when(query.getContext()).thenReturn(context);
        when(query.getFilter()).thenReturn(filter);
        when(filter.getFridgeId()).thenReturn(fridgeId);
        when(accessService.hasAccess(context.getId(), fridgeId)).thenReturn(false);

        assertThrows(FridgeAccessDeniedException.class, () -> handler.execute(query));
    }

    @Test
    void execute_returnPageResult() {
        var query = mock(ListFridgeItemsQuery.class);
        var filter = mock(FridgeItemQueryFilter.class);
        long fridgeId = 1L;

        when(query.getContext()).thenReturn(context);
        when(query.getFilter()).thenReturn(filter);
        when(filter.getFridgeId()).thenReturn(fridgeId);
        when(accessService.hasAccess(context.getId(), fridgeId)).thenReturn(true);
        when(repository.get(filter)).thenReturn(new FridgeItemPage(1, 2, 3, 4L, true, false, List.of()));

        PageResult<FridgeItemDetails> result = handler.execute(query);

        assertNotNull(result);
        assertInstanceOf(PageResult.class, result);
    }
}