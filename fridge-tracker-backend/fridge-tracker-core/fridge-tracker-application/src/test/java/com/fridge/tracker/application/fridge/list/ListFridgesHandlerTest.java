package com.fridge.tracker.application.fridge.list;

import com.fridge.tracker.application.shared.cqrs.QueryHandler;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import com.fridge.tracker.domain.fridge.model.FridgePage;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fridge.tracker.application.shared.mock.ContextMock.context;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListFridgesHandlerTest {
    @Mock
    FridgeRepository repository;

    QueryHandler<ListFridgesQuery, PageResult<FridgeDetails>> handler;

    @BeforeEach
    void initialize() {
        handler = new ListFridgesHandler(repository);
    }

    @Test
    void execute_returnPageResult() {
        var query = mock(ListFridgesQuery.class);

        when(query.getContext()).thenReturn(context);
        when(repository.get(anyString(), any()))
                .thenReturn(new FridgePage(0, 10, 10, 100, true, false, List.of()));

        PageResult<FridgeDetails> result = handler.execute(query);

        assertNotNull(result);
        assertInstanceOf(PageResult.class, result);
    }
}