package com.fridge.tracker.persistence.sql.shared.query.mapper;

import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.fridge.tracker.domain.shared.query.sort.enumeration.SortDirection.ASC;
import static java.util.Objects.isNull;

@Component
public class SortColumnsMapperImpl implements SortColumnsMapper {

    @Override
    public Sort map(List<SortColumn> sortColumns) {
        if (isNull(sortColumns) || sortColumns.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = sortColumns.stream()
                .map(this::map)
                .toList();

        return Sort.by(orders);
    }

    private Sort.Order map(SortColumn sortColumn) {
        Sort.Direction direction = sortColumn.getDirection() == ASC
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return new Sort.Order(direction, sortColumn.getName());
    }
}
