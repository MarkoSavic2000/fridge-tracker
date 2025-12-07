package com.fridge.tracker.application.shared.cqrs;

import com.fridge.tracker.application.shared.context.interfaces.ContextResolver;
import com.fridge.tracker.application.shared.cqrs.exception.HandlerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@SuppressWarnings("unchecked")
@Slf4j
@Component
public class BusImpl implements Bus {
    private final Map<Class<? extends Command>, CommandHandler<? extends Command, ?>> commandHandlers = new HashMap<>();
    private final Map<Class<? extends Query>, QueryHandler<? extends Query, ?>> queryHandlers = new HashMap<>();
    private final ContextResolver resolver;

    public BusImpl(
            List<CommandHandler<? extends Command, ?>> commandHandlers,
            List<QueryHandler<? extends Query, ?>> queryHandlers,
            ContextResolver resolver
    ) {
        this.resolver = resolver;
        for (CommandHandler<? extends Command, ?> handler : commandHandlers) {
            Class<?> commandType = resolveGenericParameterType(handler, CommandHandler.class);
            if (nonNull(commandType)) {
                this.commandHandlers.put((Class<? extends Command>) commandType, handler);
            }
        }

        for (QueryHandler<? extends Query, ?> handler : queryHandlers) {
            Class<?> queryType = resolveGenericParameterType(handler, QueryHandler.class);
            if (nonNull(queryType)) {
                this.queryHandlers.put((Class<? extends Query>) queryType, handler);
            }
        }
    }

    @Override
    public <C extends Command, R> R execute(C command) {
        setContext(command);
        CommandHandler<C, R> handler = (CommandHandler<C, R>) commandHandlers.get(command.getClass());
        if (nonNull(handler)) {
            return handler.execute(command);
        } else {
            throw new HandlerNotFoundException(command.getClass());
        }
    }

    @Override
    public <Q extends Query, R> R execute(Q query) {
        setContext(query);
        QueryHandler<Q, R> handler = (QueryHandler<Q, R>) queryHandlers.get(query.getClass());
        if (nonNull(handler)) {
            return handler.execute(query);
        } else {
            throw new HandlerNotFoundException(query.getClass());
        }
    }

    private Class<?> resolveGenericParameterType(Object handler, Class<?> expectedInterface) {
        Class<?> targetClass = AopUtils.getTargetClass(handler);
        for (Type type : targetClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType parameterizedType) {
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class<?> rawClass && expectedInterface.isAssignableFrom(rawClass)) {
                    Type param = parameterizedType.getActualTypeArguments()[0];
                    if (param instanceof Class<?> clazz) {
                        return clazz;
                    }
                }
            }
        }

        log.warn("Unable to resolve generic type for handler: {}", targetClass.getName());

        return null;
    }

    private void setContext(Action action) {
        action.setContext(resolver.resolve());
    }
}
