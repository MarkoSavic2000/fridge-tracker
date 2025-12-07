package com.fridge.tracker.rest.fridge_item;

import com.fridge.tracker.application.fridge_item.add.AddFridgeItemCommand;
import com.fridge.tracker.application.fridge_item.delete.DeleteFridgeItemCommand;
import com.fridge.tracker.application.fridge_item.list.ListFridgeItemsQuery;
import com.fridge.tracker.application.fridge_item.take.TakeFridgeItemCommand;
import com.fridge.tracker.application.shared.cqrs.Bus;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemDetails;
import com.fridge.tracker.domain.fridge_item.model.FridgeItemRecord;
import com.fridge.tracker.rest.FridgeItemApi;
import com.fridge.tracker.rest.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FridgeItemController implements FridgeItemApi {
    private final Bus bus;
    private final FridgeItemMapper mapper;

    @Override
    public ResponseEntity<ResultResponse> addFridgeItem(Long fridgeId, AddFridgeItemRequest request) {
        AddFridgeItemCommand command = mapper.map(fridgeId, request);
        Result<FridgeItemRecord> result = bus.execute(command);
        ResultResponse response = mapper.map(result);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResultResponse> deleteFridgeItem(Long id) {
        var command = new DeleteFridgeItemCommand(id);
        Result<Long> result = bus.execute(command);
        ResultResponse response = mapper.map(result);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResultResponse> takeFridgeItem(Long itemId, TakeFridgeItemRequest request) {
        var command = new TakeFridgeItemCommand(itemId, request.getQuantity());
        Result<Long> result = bus.execute(command);
        ResultResponse response = mapper.map(result);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FridgeItemPage> listFridgeItems(Long fridgeId, Integer page, Integer size,
                                                          List<String> sort, String name, FridgeItemCategoryEnumApi category,
                                                          LocalDate storedOnFrom, LocalDate storedOnTo,
                                                          LocalDate expiresOnFrom, LocalDate expiresOnTo, Boolean onlyExpired) {
        ListFridgeItemsQuery query = mapper.map(fridgeId, page, size, sort, name, category, storedOnFrom, storedOnTo,
                expiresOnFrom, expiresOnTo, onlyExpired);
        PageResult<FridgeItemDetails> result = bus.execute(query);
        FridgeItemPage response = mapper.map(result);
        return ResponseEntity.ok(response);
    }


}
