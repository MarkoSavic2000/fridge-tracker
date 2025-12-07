package com.fridge.tracker.application.shared.cqrs;

public interface QueryHandler<Q extends Query, R> {

    /**
     * Executes given query.
     *
     * @param query query which is executed by the handler
     * @return result of type {@link R}
     */
    R execute(Q query);
}
