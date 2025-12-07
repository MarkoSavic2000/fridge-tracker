package com.fridge.tracker.rest.fridge;

import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import com.fridge.tracker.rest.model.FridgeDetailsApi;
import com.fridge.tracker.rest.model.FridgePage;
import com.fridge.tracker.rest.shared.mapper.CommonMapper;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FridgeMapper extends CommonMapper {

    List<FridgeDetailsApi> map(List<FridgeDetails> details);

    default FridgePage map(PageResult<FridgeDetails> result) {
        return new FridgePage()
                .page(result.getPageNumber())
                .size(result.getPageSize())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .hasNext(result.isHasNext())
                .hasPrevious(result.isHasPrevious())
                .content(this.map(result.getValue()));
    }
}
