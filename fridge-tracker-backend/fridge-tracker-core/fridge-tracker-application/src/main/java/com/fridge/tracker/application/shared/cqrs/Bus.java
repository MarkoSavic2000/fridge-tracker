package com.fridge.tracker.application.shared.cqrs;

/**
 * Forwards commands and queries to their handlers which executes them.
 */
public interface Bus {

    /**
     * Executes given command.
     *
     * @param command command to execute
     * @return result of command execution
     */
    <C extends Command, R> R execute(C command);

    /**
     * Executes given query.
     *
     * @param query query to execute
     * @return result of query execution
     */
    <Q extends Query, R> R execute(Q query);
}
