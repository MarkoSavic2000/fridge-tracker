package com.fridge.tracker.rest.fridge;

import com.fridge.tracker.application.fridge.create.CreateFridgeCommand;
import com.fridge.tracker.application.fridge.delete.DeleteFridgeCommand;
import com.fridge.tracker.application.fridge.list.ListFridgesQuery;
import com.fridge.tracker.application.shared.cqrs.Bus;
import com.fridge.tracker.application.shared.cqrs.result.CreatedResult;
import com.fridge.tracker.application.shared.cqrs.result.DeletedResult;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.rest.FridgeApi;
import com.fridge.tracker.rest.model.CreateFridgeRequest;
import com.fridge.tracker.rest.model.FridgePage;
import com.fridge.tracker.rest.model.ResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FridgeControllerTest {
    @Mock
    Bus bus;

    @Mock
    FridgeMapper mapper;

    FridgeApi controller;

    @BeforeEach
    void initialize() {
        controller = new FridgeController(bus, mapper);
    }

    @Test
    void createFridge_returnResponse() {
        var result = new CreatedResult<>(new FridgeRecord("F1"));
        var expectedResponse = new ResultResponse();

        when(bus.execute(any(CreateFridgeCommand.class))).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<ResultResponse> response = controller.createFridge(new CreateFridgeRequest());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void deleteFridge_returnResponse() {
        var result = new DeletedResult<>(1L);
        var expectedResponse = new ResultResponse();

        when(bus.execute(any(DeleteFridgeCommand.class))).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<ResultResponse> response = controller.deleteFridge(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void listFridges_returnResponse() {
        var result = mock(PageResult.class);
        var expectedResponse = new FridgePage();

        when(bus.execute(any(ListFridgesQuery.class))).thenReturn(result);
        when(mapper.map(result)).thenReturn(expectedResponse);

        ResponseEntity<FridgePage> response = controller.listFridges(0, 10, List.of(), "F");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}