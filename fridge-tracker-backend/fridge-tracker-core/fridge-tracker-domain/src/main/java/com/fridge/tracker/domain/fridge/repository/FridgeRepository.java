package com.fridge.tracker.domain.fridge.repository;

import com.fridge.tracker.domain.fridge.model.FridgePage;
import com.fridge.tracker.domain.fridge.model.FridgeQueryFilter;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;

public interface FridgeRepository {

    /**
     * Saves fridge.
     *
     * @param fridge fridge to save
     */
    void save(FridgeRecord fridge);

    /**
     * Checks if the with given keycloak ID has fridge with given name.
     *
     * @param keycloakId user's keycloak unique identifier
     * @param name       name of the fridge
     * @return {@code true} if has; otherwise {@code false}
     */
    boolean exists(String keycloakId, String name);

    /**
     * Checks if the fridge with given ID exists.
     *
     * @param id unique identifier of the fridge
     * @return {@code true} if exists; otherwise {@code false}
     */
    boolean exists(Long id);

    /**
     * Deletes fridge with given ID.
     *
     * @param id unique identifier of the fridge
     */
    void delete(Long id);

    /**
     * Retrieves fridges for the given user based on the given filter.
     *
     * @param keycloakId user's keycloak unique identifier
     * @param filter     contains filter data
     * @return {@link FridgePage
     */
    FridgePage get(String keycloakId, FridgeQueryFilter filter);
}
