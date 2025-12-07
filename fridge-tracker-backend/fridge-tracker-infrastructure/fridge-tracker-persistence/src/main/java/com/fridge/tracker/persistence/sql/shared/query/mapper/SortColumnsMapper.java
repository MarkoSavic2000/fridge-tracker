package com.fridge.tracker.persistence.sql.shared.query.mapper;

import com.fridge.tracker.domain.shared.query.sort.SortColumn;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SortColumnsMapper {

    /**
     * Maps list of {@link SortColumn} to the {@link Sort}
     *
     * @param sortColumns sort columns to map
     * @return {@link Sort}
     */
    Sort map(List<SortColumn> sortColumns);
}
