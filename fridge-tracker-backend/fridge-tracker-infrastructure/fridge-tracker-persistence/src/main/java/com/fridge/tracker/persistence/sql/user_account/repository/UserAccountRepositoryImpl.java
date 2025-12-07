package com.fridge.tracker.persistence.sql.user_account.repository;

import com.fridge.tracker.domain.user_account.model.UserAccount;
import com.fridge.tracker.domain.user_account.repository.UserAccountRepository;
import com.fridge.tracker.persistence.sql.user_account.entity.UserAccountEntity;
import com.fridge.tracker.persistence.sql.user_account.mapper.UserAccountEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final UserAccountJpaRepository jpaRepository;
    private final UserAccountEntityMapper mapper;

    @Override
    public UserAccount save(UserAccount userAccount) {
        UserAccountEntity entity = mapper.map(userAccount);
        UserAccountEntity savedEntity = jpaRepository.save(entity);
        return mapper.map(savedEntity);
    }

    @Override
    public Optional<UserAccount> get(String keycloakId) {
        UserAccountEntity entity = jpaRepository.findByKeycloakId(keycloakId);
        return Optional.ofNullable(mapper.map(entity));
    }
}
