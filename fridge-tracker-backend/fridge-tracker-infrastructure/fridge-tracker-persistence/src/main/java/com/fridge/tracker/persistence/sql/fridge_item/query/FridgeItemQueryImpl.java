package com.fridge.tracker.persistence.sql.fridge_item.query;

import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity;
import com.fridge.tracker.persistence.sql.shared.query.mapper.SortColumnsMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.fridge.tracker.persistence.sql.fridge_item.entity.FridgeItemEntity.Fields.*;
import static com.fridge.tracker.persistence.sql.shared.query.PredicateUtils.addPredicate;

@Repository
@RequiredArgsConstructor
public class FridgeItemQueryImpl implements FridgeItemQuery {
    private final EntityManager em;
    private final SortColumnsMapper sortColumnsMapper;

    @Override
    public Page<FridgeItemEntity> get(FridgeItemQueryFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FridgeItemEntity> cq = cb.createQuery(FridgeItemEntity.class);
        Root<FridgeItemEntity> root = cq.from(FridgeItemEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        addPredicate(() -> fridgeIn(filter, cb, root), predicates);
        addPredicate(() -> activeIn(cb, root), predicates);
        addPredicate(filter.isNameProvided(), () -> nameIn(filter, cb, root), predicates);
        addPredicate(filter.isCategoryProvided(), () -> categoryIn(filter, cb, root), predicates);
        addPredicate(filter.isStoredOnFromProvided(), () -> storedOnFrom(filter, cb, root), predicates);
        addPredicate(filter.isStoredOnToProvided(), () -> storedOnTo(filter, cb, root), predicates);
        addPredicate(filter.isExpiresOnFromProvided(), () -> expiresOnFrom(filter, cb, root), predicates);
        addPredicate(filter.isExpiresOnToProvided(), () -> expiresOnTo(filter, cb, root), predicates);
        addPredicate(filter.retrieveOnlyExpired(), () -> retrieveOnlyExpired(cb, root), predicates);

        cq.where(predicates.toArray(new Predicate[0]));

        Sort sort = sortColumnsMapper.map(filter.getSortColumns());
        Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(), sort);

        if (pageable.getSort().isSorted()) {
            List<Order> orders = getOrders(pageable, root, cb);
            cq.orderBy(orders);
        }

        TypedQuery<FridgeItemEntity> query = em.createQuery(cq);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<FridgeItemEntity> countRoot = countQuery.from(FridgeItemEntity.class);
        countQuery.select(cb.count(countRoot));
        countQuery.where(predicates.toArray(new Predicate[0]));

        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);

    }

    private List<Order> getOrders(Pageable pageable, Root<FridgeItemEntity> root, CriteriaBuilder cb) {
        List<Order> orders = new ArrayList<>();
        pageable.getSort().forEach(order -> {
            Path<Object> path = root.get(order.getProperty());
            orders.add(order.isAscending() ? cb.asc(path) : cb.desc(path));
        });

        return orders;
    }

    private Predicate fridgeIn(FridgeItemQueryFilter filter, CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.equal(root.get(fridgeId), filter.getFridgeId());
    }

    private Predicate activeIn(CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.isFalse(root.get(deleted));
    }

    private Predicate nameIn(FridgeItemQueryFilter filter, CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.like(cb.lower(root.get(name)), "%" + filter.getName().toLowerCase() + "%");
    }

    private Predicate categoryIn(FridgeItemQueryFilter filter, CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.equal(root.get(category), filter.getCategory());
    }

    private Predicate storedOnFrom(FridgeItemQueryFilter filter, CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.greaterThanOrEqualTo(root.get(storedOn), filter.getStoredOnFrom());
    }

    private Predicate storedOnTo(FridgeItemQueryFilter filter, CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.lessThanOrEqualTo(root.get(storedOn), filter.getStoredOnTo());
    }

    private Predicate expiresOnFrom(FridgeItemQueryFilter filter, CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.greaterThanOrEqualTo(root.get(expiresOn), filter.getExpiresOnFrom());
    }

    private Predicate expiresOnTo(FridgeItemQueryFilter filter, CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.lessThanOrEqualTo(root.get(expiresOn), filter.getExpiresOnTo());
    }

    private Predicate retrieveOnlyExpired(CriteriaBuilder cb, Root<FridgeItemEntity> root) {
        return cb.lessThan(root.get(expiresOn), LocalDate.now());
    }
}
