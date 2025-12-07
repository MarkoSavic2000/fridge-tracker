package com.fridge.tracker.rest.fridge_item;

import com.fridge.tracker.application.fridge_item.add.AddFridgeItemCommand;
import com.fridge.tracker.application.fridge_item.delete.DeleteFridgeItemCommand;
import com.fridge.tracker.application.fridge_item.list.ListFridgeItemsQuery;
import com.fridge.tracker.application.fridge_item.take.TakeFridgeItemCommand;
import com.fridge.tracker.application.shared.cqrs.Bus;
import com.fridge.tracker.application.shared.cqrs.result.CreatedResult;
import com.fridge.tracker.application.shared.cqrs.result.DeletedResult;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.application.shared.cqrs.result.UpdatedResult;
import com.fridge.tracker.rest.FridgeItemApi;
import com.fridge.tracker.rest.model.AddFridgeItemRequest;
import com.fridge.tracker.rest.model.FridgeItemPage;
import com.fridge.tracker.rest.model.ResultResponse;
import com.fridge.tracker.rest.model.TakeFridgeItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FridgeItemControllerTest {
    @Mock
    Bus bus;

    @Mock
    FridgeItemMapper mapper;

    FridgeItemApi controller;

    @BeforeEach
    void initialize() {
        controller = new FridgeItemController(bus, mapper);
    }

    @Test
    void addFridgeItem_returnResponse() {
        var command = mock(AddFridgeItemCommand.class);
        var result = mock(CreatedResult.class);
        var expectedResponse = new ResultResponse();

        when(mapper.map(anyLong(), any(AddFridgeItemRequest.class))).thenReturn(command);
        when(bus.execute(command)).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<ResultResponse> response = controller.addFridgeItem(1L, new AddFridgeItemRequest());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void deleteFridgeItem_returnResponse() {
        var result = mock(DeletedResult.class);
        var expectedResponse = new ResultResponse();

        when(bus.execute(any(DeleteFridgeItemCommand.class))).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<ResultResponse> response = controller.deleteFridgeItem(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void takeFridgeItem_returnResponse() {
        var result = mock(UpdatedResult.class);
        var expectedResponse = new ResultResponse();

        when(bus.execute(any(TakeFridgeItemCommand.class))).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<ResultResponse> response = controller.takeFridgeItem(1L, new TakeFridgeItemRequest());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @SuppressWarnings("unchecked")
    void listFridgeItems_returnResponse() {
        var query = mock(ListFridgeItemsQuery.class);
        var result = mock(PageResult.class);
        var expectedResponse = new FridgeItemPage();

        when(mapper.map(anyLong(), any(), any(), anyList(), anyString(), any(), any(), any(), any(), any(), anyBoolean()))
                .thenReturn(query);
        when(bus.execute(query)).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<FridgeItemPage> response = controller.listFridgeItems(anyLong(), any(), any(), anyList(),
                anyString(), any(), any(), any(), any(), any(), anyBoolean());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}