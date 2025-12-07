package com.fridge.tracker.persistence.sql.fridge.repository;

import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import com.fridge.tracker.persistence.sql.fridge.entity.FridgeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FridgeJpaRepository extends JpaRepository<FridgeEntity, Long> {

    /**
     * Checks if user with given keycloak ID has fridge with given name.
     *
     * @param keycloakId user's keycloak unique identifier
     * @param name       name of the fridge
     * @return {@code true} if has; otherwise {@code false}
     */
    @Query("""
            SELECT COUNT(f) > 0
            FROM FridgeEntity f
            JOIN f.userAccount u
            WHERE u.keycloakId = :keycloakId AND f.name = :name
            """)
    boolean exists(String keycloakId, String name);

    @Query("""
             SELECT
                 f.id AS id,
                 f.name AS name,
                 f.createdOn AS createdOn
             FROM FridgeEntity f
             JOIN f.userAccount u
             WHERE u.keycloakId = :keycloakId
               AND LOWER(f.name) LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
            """)
    Page<FridgeDetails> get(String keycloakId, String name, Pageable pageable);

    /**
     * Checks if user with given keycloak ID has access to the fridge with given ID.
     *
     * @param keycloakId user's keycloak unique identifier
     * @param fridgeId   unique identifier of the fridge
     * @return {@code true} if has; otherwise {@code false}
     */
    @Query("""
            SELECT COUNT(f) > 0
            FROM FridgeEntity f
            JOIN f.userAccount u
            WHERE u.keycloakId = :keycloakId AND f.id = :fridgeId
            """)
    boolean hasAccess(String keycloakId, Long fridgeId);

    /**
     * Checks if user with given keycloak ID has access to the fridge item with given ID.
     *
     * @param keycloakId   user's keycloak unique identifier
     * @param fridgeItemId unique identifier of the fridge item
     * @return {@code true} if has; otherwise {@code false}
     */
    @Query("""
            SELECT COUNT(f) > 0
            FROM FridgeItemEntity fi
            JOIN fi.fridgeEntity f
            JOIN f.userAccount u
            WHERE u.keycloakId = :keycloakId AND fi.id = :fridgeItemId AND fi.deleted = false
            """)
    boolean hasFridgeItemAccess(String keycloakId, Long fridgeItemId);

    /**
     * Gets ID of fridge which owns fridge item with given ID
     *
     * @param fridgeItemId unique identifier of the fridge item
     * @return unique identifier of the fridge
     */
    @Query("""
            SELECT f.id
            FROM FridgeEntity f
            JOIN f.items fi
            WHERE fi.id = :fridgeItemId
            """)
    Long getId(Long fridgeItemId);

}
