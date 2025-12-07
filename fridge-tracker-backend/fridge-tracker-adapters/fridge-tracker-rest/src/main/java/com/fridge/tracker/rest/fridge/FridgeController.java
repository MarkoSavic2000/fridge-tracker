package com.fridge.tracker.rest.fridge;

import com.fridge.tracker.application.fridge.create.CreateFridgeCommand;
import com.fridge.tracker.application.fridge.delete.DeleteFridgeCommand;
import com.fridge.tracker.application.fridge.list.ListFridgesQuery;
import com.fridge.tracker.application.shared.cqrs.Bus;
import com.fridge.tracker.application.shared.cqrs.result.PageResult;
import com.fridge.tracker.application.shared.cqrs.result.Result;
import com.fridge.tracker.domain.fridge.model.FridgeDetails;
import com.fridge.tracker.domain.fridge.model.FridgeQueryFilter;
import com.fridge.tracker.domain.fridge.model.FridgeRecord;
import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import com.fridge.tracker.rest.FridgeApi;
import com.fridge.tracker.rest.model.CreateFridgeRequest;
import com.fridge.tracker.rest.model.FridgePage;
import com.fridge.tracker.rest.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
public class FridgeController implements FridgeApi {
    private final Bus bus;
    private final FridgeMapper mapper;

    @Override
    public ResponseEntity<ResultResponse> createFridge(CreateFridgeRequest request) {
        var command = new CreateFridgeCommand(new FridgeRecord(request.getName()));
        Result<FridgeRecord> result = bus.execute(command);
        ResultResponse response = mapper.map(result);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResultResponse> deleteFridge(Long id) {
        var command = new DeleteFridgeCommand(id);
        Result<Long> result = bus.execute(command);
        ResultResponse response = mapper.map(result);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FridgePage> listFridges(Integer page, Integer size, List<String> sort, String name) {
        List<SortColumn> sortColumns = nonNull(sort) ? sort.stream().map(SortColumn::new).toList() : null;
        var query = new ListFridgesQuery(new FridgeQueryFilter(page, size, sortColumns, name));
        PageResult<FridgeDetails> result = bus.execute(query);
        FridgePage response = mapper.map(result);
        return ResponseEntity.ok(response);
    }
}
