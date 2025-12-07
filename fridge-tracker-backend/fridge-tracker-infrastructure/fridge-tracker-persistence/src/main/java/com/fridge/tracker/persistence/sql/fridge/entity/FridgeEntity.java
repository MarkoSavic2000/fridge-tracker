package com.fridge.tracker.persistence.sql.fridge.entity;

import com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity;
import com.fridge.tracker.persistence.sql.user_account.entity.UserAccountEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "fridge")
@Getter
@Setter
public class FridgeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_on", insertable = false, updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "user_account_id")
    private Long userAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserAccountEntity userAccount;

    @OneToMany(
            mappedBy = "fridgeEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<FridgeItemEntity> items = new ArrayList<>();
}
