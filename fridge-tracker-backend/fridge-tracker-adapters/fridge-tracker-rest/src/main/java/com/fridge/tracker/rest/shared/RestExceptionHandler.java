package com.fridge.tracker.rest.shared;

import com.fridge.tracker.application.recipe.generate.exception.NoAvailableIngredientsException;
import com.fridge.tracker.application.shared.security.exception.FridgeAccessDeniedException;
import com.fridge.tracker.application.shared.security.exception.FridgeItemAccessDeniedException;
import com.fridge.tracker.application.shared.validation.exception.ValidationException;
import com.fridge.tracker.rest.model.ErrorResponse;
import com.fridge.tracker.rest.shared.mapper.ErrorResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final ErrorResponseMapper mapper;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);

        ErrorResponse response = mapper.map(exception.getMessage());
        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResponse> handleException(ValidationException exception) {
        log.error(exception.getMessage());

        ErrorResponse response = mapper.map(exception.getErrors());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler({FridgeAccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleException(FridgeAccessDeniedException exception) {
        log.error(exception.getError());

        ErrorResponse response = mapper.map(exception.getMessage());
        return new ResponseEntity<>(response, FORBIDDEN);
    }

    @ExceptionHandler({FridgeItemAccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleException(FridgeItemAccessDeniedException exception) {
        log.error(exception.getError());

        ErrorResponse response = mapper.map(exception.getMessage());
        return new ResponseEntity<>(response, FORBIDDEN);
    }

    @ExceptionHandler({NoAvailableIngredientsException.class})
    public ResponseEntity<ErrorResponse> handleException(NoAvailableIngredientsException exception) {
        ErrorResponse response = mapper.map(exception.getMessage());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
}
