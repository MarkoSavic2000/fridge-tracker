package com.fridge.tracker.persistence.sql.user_account.mapper;

import com.fridge.tracker.domain.user_account.model.UserAccount;
import com.fridge.tracker.persistence.sql.user_account.entity.UserAccountEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserAccountEntityMapper {

    UserAccount map(UserAccountEntity userAccountEntity);

    UserAccountEntity map(UserAccount userAccount);
}
