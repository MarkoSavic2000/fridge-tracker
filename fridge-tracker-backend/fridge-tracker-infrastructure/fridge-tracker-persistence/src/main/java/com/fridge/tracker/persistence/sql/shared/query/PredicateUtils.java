package com.fridge.tracker.persistence.sql.shared.query;

import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PredicateUtils {

    /**
     * Adds the predicate returned by {@code supplier} if the {@code condition} is fulfilled.
     *
     * @param condition  determines should predicate be included
     * @param supplier   creates predicate
     * @param predicates list to add predicates to
     */
    public static void addPredicate(boolean condition, Supplier<Predicate> supplier, List<Predicate> predicates) {
        if (condition) {
            predicates.add(supplier.get());
        }
    }

    /**
     * Adds the predicate returned by {@code supplier}.
     *
     * @param supplier   creates predicate
     * @param predicates list to add predicates to
     */
    public static void addPredicate(Supplier<Predicate> supplier, List<Predicate> predicates) {
        predicates.add(supplier.get());
    }
}
