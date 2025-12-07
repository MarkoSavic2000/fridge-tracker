package com.fridge.tracker.domain.shared.query.sort;

import com.fridge.tracker.domain.shared.query.sort.enumeration.SortDirection;
import lombok.Getter;

@Getter
public class SortColumn {
    private final String name;
    private final SortDirection direction;

    /**
     * Creates a {@link SortColumn} from a sort parameter in the format "property-asc/desc".
     *
     * @param sortParam raw sorting parameter (e.g., "name-asc")
     */
    public SortColumn(String sortParam) {
        String[] split = sortParam.split("-");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid sort parameter: " + sortParam);
        }

        this.name = split[0];

        try {
            this.direction = SortDirection.valueOf(split[1].trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Sort direction must be ASC or DESC");
        }
    }
}
