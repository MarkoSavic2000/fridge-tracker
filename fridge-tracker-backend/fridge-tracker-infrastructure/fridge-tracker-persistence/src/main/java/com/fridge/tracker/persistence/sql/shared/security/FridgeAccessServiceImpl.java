package com.fridge.tracker.persistence.sql.shared.security;

import com.fridge.tracker.application.shared.security.FridgeAccessService;
import com.fridge.tracker.persistence.sql.fridge.repository.FridgeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FridgeAccessServiceImpl implements FridgeAccessService {
    private final FridgeJpaRepository repository;

    @Override
    public boolean hasAccess(String userId, Long fridgeId) {
        return repository.hasAccess(userId, fridgeId);
    }

    @Override
    public boolean hasFridgeItemAccess(String userId, Long fridgeItemId) {
        return repository.hasFridgeItemAccess(userId, fridgeItemId);
    }
}
