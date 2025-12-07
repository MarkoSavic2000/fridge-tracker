package com.fridge.tracker.domain.user_account.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAccount {
    private Long id;
    private String email;
    private String keycloakId;

    public UserAccount(String email, String keycloakId) {
        this.email = email;
        this.keycloakId = keycloakId;
    }
}
