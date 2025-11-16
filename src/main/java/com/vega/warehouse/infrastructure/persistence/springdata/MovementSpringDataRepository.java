package com.vega.warehouse.infrastructure.persistence.springdata;

import com.vega.warehouse.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementSpringDataRepository extends JpaRepository<MovementEntity, Long> {
}