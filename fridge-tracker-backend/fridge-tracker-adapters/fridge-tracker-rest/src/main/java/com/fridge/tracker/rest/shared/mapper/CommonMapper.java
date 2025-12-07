package com.fridge.tracker.rest.shared.mapper;

import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.rest.model.ResultResponse;

/**
 * Contains mapping methods that are used in multiple mappers.
 */
public interface CommonMapper {

    default ResultResponse map(Result<?> result) {
        return new ResultResponse()
                .success(result.isSuccess());
    }
}
