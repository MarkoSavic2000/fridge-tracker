package com.fridge.tracker.persistence.sql.fridge_item.repository;

import com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FridgeItemJpaRepository extends JpaRepository<FridgeItemEntity, Long> {

    /**
     * Performs logical deletion of fridge item with given ID.
     *
     * @param id unique identifier of the fridge item
     */
    @Modifying
    @Query("""
            UPDATE FridgeItemEntity fi
            SET fi.deleted = true
            WHERE fi.id = :id
            """)
    void delete(Long id);

    /**
     * Gets quantity of fridge item with given ID.
     *
     * @param id unique identifier of the fridge item
     * @return current quantity of fridge item
     */
    @Query("""
            SELECT fi.quantity
            FROM FridgeItemEntity fi
            WHERE fi.id = :id
            """)
    BigDecimal getQuantity(Long id);

    /**
     * Decreases quantity of fridge item for the given amount.
     *
     * @param fridgeItemId unique identifier of the fridge item
     * @param amount       amount for which current quantity will be decreased
     * @return number of updated rows
     */
    @Modifying
    @Query("""
            UPDATE FridgeItemEntity fi
            SET fi.quantity = fi.quantity - :amount
            WHERE fi.id = :fridgeItemId
              AND fi.quantity >= :amount
            """)
    int decreaseQuantity(Long fridgeItemId, BigDecimal amount);


    /**
     * Gets fridge items that can be used from the fridge with given ID.
     *
     * @param fridgeId    unique identifier of the fridge
     * @param currentDate current date
     * @return list of fridge item names
     */
    @Query("""
            SELECT fi.name
            FROM FridgeItemEntity fi
            WHERE fi.fridgeId = :fridgeId AND fi.deleted = false
               AND fi.quantity > 0 AND fi.expiresOn > :currentDate
            """)
    List<String> getAvailableFridgeItems(Long fridgeId, LocalDate currentDate);
}
