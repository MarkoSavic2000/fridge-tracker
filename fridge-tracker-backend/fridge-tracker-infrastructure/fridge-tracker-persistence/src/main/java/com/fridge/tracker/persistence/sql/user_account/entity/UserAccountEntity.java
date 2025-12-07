package com.fridge.tracker.persistence.sql.user_account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_account")
@Getter
@Setter
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "keycloak_id")
    private String keycloakId;

    @Column(name = "email")
    private String email;
}
