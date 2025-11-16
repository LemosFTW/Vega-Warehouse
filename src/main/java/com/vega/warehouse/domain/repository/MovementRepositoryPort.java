package com.vega.warehouse.domain.repository;

import com.vega.warehouse.domain.enums.MovementSortBy;
import com.vega.warehouse.domain.enums.SortDirection;
import com.vega.warehouse.domain.model.Movement;

import java.util.List;

public interface MovementRepositoryPort {

    List<Movement> findAllSorted(MovementSortBy sortBy, SortDirection direction);
}