package com.fridge.tracker.persistence.sql.fridge_item.entity;

import com.fridge.tracker.domain.fridge_item.model.enumeration.FridgeItemCategoryEnum;
import com.fridge.tracker.persistence.sql.fridge.entity.FridgeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "fridge_item")
@Getter
@Setter
@FieldNameConstants
public class FridgeItemEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private FridgeItemCategoryEnum category;

    @Column(name = "stored_on")
    private LocalDateTime storedOn;

    @Column(name = "expires_on")
    private LocalDate expiresOn;

    @Column(name = "note")
    private String note;

    @Column(name = "created_on", insertable = false, updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "fridge_id")
    private Long fridgeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", referencedColumnName = "id", insertable = false, updatable = false)
    private FridgeEntity fridgeEntity;
}
