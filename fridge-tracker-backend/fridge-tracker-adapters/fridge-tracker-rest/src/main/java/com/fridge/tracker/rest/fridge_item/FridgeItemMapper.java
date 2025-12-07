package com.fridge.tracker.rest.fridge_item;

import com.fridge.tracker.application.fridge_item.add.AddFridgeItemCommand;
import com.fridge.tracker.application.fridge_item.list.ListFridgeItemsQuery;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemDetails;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemQueryFilter;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.domain.fridge_item.model.enumeration.FridgeItemCategoryEnum;
import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import com.fridge.tracker.rest.model.AddFridgeItemRequest;
import com.fridge.tracker.rest.model.FridgeItemCategoryEnumApi;
import com.fridge.tracker.rest.model.FridgeItemDetailsApi;
import com.fridge.tracker.rest.model.FridgeItemPage;
import com.fridge.tracker.rest.shared.mapper.CommonMapper;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FridgeItemMapper extends CommonMapper {

    FridgeItemRecord map(AddFridgeItemRequest request);

    default AddFridgeItemCommand map(Long fridgeId, AddFridgeItemRequest request) {
        FridgeItemRecord fridgeItem = this.map(request);
        fridgeItem.setFridgeId(fridgeId);
        return new AddFridgeItemCommand(fridgeItem);
    }

    List<FridgeItemDetailsApi> map(List<FridgeItemDetails> details);

    default FridgeItemPage map(PageResult<FridgeItemDetails> result) {
        return new FridgeItemPage()
                .page(result.getPageNumber())
                .size(result.getPageSize())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .hasNext(result.isHasNext())
                .hasPrevious(result.isHasPrevious())
                .content(this.map(result.getValue()));
    }

    default ListFridgeItemsQuery map(Long fridgeId, Integer page, Integer size, List<String> sort, String name,
                                     FridgeItemCategoryEnumApi category, LocalDate storedOnFrom, LocalDate storedOnTo,
                                     LocalDate expiresOnFrom, LocalDate expiresOnTo, Boolean expired) {
        List<SortColumn> sortColumns = nonNull(sort) ? sort.stream().map(SortColumn::new).toList() : null;
        FridgeItemCategoryEnum itemCategory = nonNull(category) ? FridgeItemCategoryEnum.valueOf(category.name()) : null;
        var filter = new FridgeItemQueryFilter(page, size, sortColumns, fridgeId, name, itemCategory, storedOnFrom,
                storedOnTo, expiresOnFrom, expiresOnTo, expired);
        return new ListFridgeItemsQuery(filter);
    }
}
