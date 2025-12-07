package com.fridge.tracker.rest.shared.mapper;

import com.fridge.tracker.application.shared.validation.Message;
import com.fridge.tracker.rest.model.ErrorResponse;
import com.fridge.tracker.rest.model.MessageApi;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ErrorResponseMapper {

    MessageApi map(Message message);

    List<MessageApi> mapMessages(List<Message> messages);

    default ErrorResponse map(List<Message> messages) {
        return new ErrorResponse()
                .messages(this.mapMessages(messages));
    }

    default ErrorResponse map(String message) {
        var errorMessage = new Message(true, "", message);
        return map(List.of(errorMessage));
    }
}
