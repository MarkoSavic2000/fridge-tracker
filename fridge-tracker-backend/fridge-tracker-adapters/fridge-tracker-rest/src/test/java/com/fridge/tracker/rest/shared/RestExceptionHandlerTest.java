package com.fridge.tracker.rest.shared;

import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.application.shared.security.exception.FridgeItemAccessDeniedException;
import com.fridge.tracker.application.shared.validation.ValidationReport;
import com.fridge.tracker.application.shared.validation.exception.ValidationException;
import com.fridge.tracker.rest.model.ErrorResponse;
import com.fridge.tracker.rest.shared.mapper.ErrorResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {
    @Mock
    ErrorResponseMapper mapper;

    RestExceptionHandler handler;

    @BeforeEach
    void initialize() {
        handler = new RestExceptionHandler(mapper);
    }

    @Test
    void handleException_ValidationException_returnBadRequest() {
        var exception = new ValidationException(new ValidationReport());
        var expectedResponse = new ErrorResponse();

        when(mapper.map(exception.getErrors())).thenReturn(expectedResponse);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void handleException_FridgeAccessDeniedException_returnForbidden() {
        var exception = new FridgeAccessDeniedException("12", 1L);
        var expectedResponse = new ErrorResponse();

        when(mapper.map(exception.getMessage())).thenReturn(expectedResponse);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void handleException_FridgeItemAccessDeniedException_returnForbidden() {
        var exception = new FridgeItemAccessDeniedException("12", 1L);
        var expectedResponse = new ErrorResponse();

        when(mapper.map(exception.getMessage())).thenReturn(expectedResponse);

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}