package com.fridge.tracker.application.shared.cqrs;

import com.fridge.tracker.application.shared.context.Context;
import com.fridge.tracker.application.shared.context.interfaces.ContextResolver;
import com.fridge.tracker.application.shared.cqrs.exception.HandlerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusTest {
    @Mock
    ContextResolver resolver;

    @Mock
    Context context;

    Bus bus;

    @BeforeEach
    void initialize() {
        when(resolver.resolve()).thenReturn(context);
        bus = new BusImpl(List.of(new TestCommandHandler()), List.of(new TestQueryHandler()), resolver);
    }

    @Test
    void execute_command_returnResult() {
        TestCommand command = new TestCommand();

        String result = bus.execute(command);

        assertEquals("executed", result);
        assertEquals(context, command.getContext());
    }

    @Test
    void execute_query_returnResult() {
        TestQuery query = new TestQuery();

        String result = bus.execute(query);

        assertEquals("executed", result);
        assertEquals(context, query.getContext());
    }

    @Test
    void execute_commandHandlerNotFound_throwsHandlerNotFoundException() {
        UnhandledCommand command = new UnhandledCommand();

        assertThrows(HandlerNotFoundException.class, () -> bus.execute(command));
    }

    @Test
    void execute_queryHandlerNotFound_throwsHandlerNotFoundException() {
        UnhandledQuery query = new UnhandledQuery();

        assertThrows(HandlerNotFoundException.class, () -> bus.execute(query));
    }

    static class TestCommand extends Command {
    }

    static class TestQuery extends Query {
    }

    static class UnhandledCommand extends Command {
    }

    static class UnhandledQuery extends Query {
    }

    static class TestCommandHandler implements CommandHandler<TestCommand, String> {
        @Override
        public String execute(TestCommand command) {
            return "executed";
        }
    }

    static class TestQueryHandler implements QueryHandler<TestQuery, String> {
        @Override
        public String execute(TestQuery query) {
            return "executed";
        }
    }

}