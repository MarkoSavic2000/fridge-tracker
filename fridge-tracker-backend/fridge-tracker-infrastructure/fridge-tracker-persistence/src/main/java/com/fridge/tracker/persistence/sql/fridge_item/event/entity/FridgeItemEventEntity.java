package com.fridge.tracker.persistence.sql.fridge_item.event.entity;

import com.fridge.tracker.domain.fridge_item.event.enumeration.FridgeItemEventTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "fridge_item_event")
@Getter
@Setter
public class FridgeItemEventEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fridge_item_id")
    private Long fridgeItemId;

    @Column(name = "fridge_id")
    private Long fridgeId;

    @Column(name = "keycloak_id")
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FridgeItemEventTypeEnum type;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "created_on", insertable = false, updatable = false)
    private LocalDateTime createdOn;
}
