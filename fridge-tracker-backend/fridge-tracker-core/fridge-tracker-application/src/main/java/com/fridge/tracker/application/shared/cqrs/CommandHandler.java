package com.fridge.tracker.application.shared.cqrs;

/**
 * Interface that every command handler should implement.
 *
 * @param <C> Command type
 * @param <R> Return type
 */
public interface CommandHandler<C extends Command, R> {

    /**
     * Executes given command.
     *
     * @param command command which is executed by the handler
     * @return result of type {@link R}
     */
    R execute(C command);
}
