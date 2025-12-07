package com.fridge.tracker.persistence.sql.user_account.repository;

import com.fridge.tracker.domain.user_account.model.UserAccount;
import com.fridge.tracker.persistence.sql.user_account.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountJpaRepository extends JpaRepository<UserAccountEntity, Long> {

    /**
     * Gets user account with given keycloak ID.
     *
     * @param keycloakId user's keycloak unique identifier
     * @return {@link UserAccount}
     */
    UserAccountEntity findByKeycloakId(String keycloakId);
}
