package com.fridge.tracker.rest.shared.mapper;

import com.fridge.tracker.rest.model.ErrorResponse;
import com.fridge.tracker.rest.model.MessageApi;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseMapperTest {
    ErrorResponseMapper mapper = new ErrorResponseMapperImpl();

    @Test
    void map_returnErrorResponse() {
        String errorMessage = "id.invalid";

        ErrorResponse result = mapper.map(errorMessage);

        assertNotNull(result);

        List<MessageApi> messages = result.getMessages();
        assertNotNull(messages);
        assertEquals(1, messages.size());

        MessageApi message = messages.getFirst();
        assertNotNull(message);
        assertTrue(message.getGlobal());
        assertEquals("", message.getReference());
        assertEquals(errorMessage, message.getLabel());
    }
}