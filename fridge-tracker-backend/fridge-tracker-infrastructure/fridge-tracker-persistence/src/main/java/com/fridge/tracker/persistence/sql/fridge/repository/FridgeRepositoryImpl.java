package com.fridge.tracker.persistence.sql.fridge.repository;

import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import com.fridge.tracker.domain.fridge.model.FridgePage;
import com.fridge.tracker.domain.fridge.model.FridgeQueryFilter;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.domain.fridge.repository.FridgeRepository;
import com.fridge.tracker.persistence.sql.fridge.entity.FridgeEntity;
import com.fridge.tracker.persistence.sql.fridge.mapper.FridgeEntityMapper;
import com.fridge.tracker.persistence.sql.shared.query.mapper.SortColumnsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FridgeRepositoryImpl implements FridgeRepository {
    private final FridgeJpaRepository jpaRepository;
    private final FridgeEntityMapper mapper;
    private final SortColumnsMapper sortColumnsMapper;

    @Override
    public void save(FridgeRecord fridge) {
        FridgeEntity entity = mapper.map(fridge);
        jpaRepository.save(entity);
    }

    @Override
    public boolean exists(String keycloakId, String name) {
        return jpaRepository.exists(keycloakId, name);
    }

    @Override
    public boolean exists(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public FridgePage get(String keycloakId, FridgeQueryFilter filter) {
        Sort sort = sortColumnsMapper.map(filter.getSortColumns());
        Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(), sort);
        Page<FridgeDetails> page = jpaRepository.get(keycloakId, filter.getName(), pageable);

        return new FridgePage(
                filter.getPageNumber(),
                filter.getPageSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious(),
                page.getContent()
        );
    }
}
