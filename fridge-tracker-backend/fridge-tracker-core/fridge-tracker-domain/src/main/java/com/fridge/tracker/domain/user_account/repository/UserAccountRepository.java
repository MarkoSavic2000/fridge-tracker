package com.fridge.tracker.domain.user_account.repository;

import com.fridge.tracker.domain.user_account.model.UserAccount;

import java.util.Optional;

public interface UserAccountRepository {

    /**
     * Saves user account.
     *
     * @param userAccount user account to save
     * @return saved user account
     */
    UserAccount save(UserAccount userAccount);

    /**
     * Gets user account with given keycloak ID if it exists.
     *
     * @param keycloakId user's keycloak unique identifier
     * @return optional of {@link UserAccount}
     */
    Optional<UserAccount> get(String keycloakId);
}
