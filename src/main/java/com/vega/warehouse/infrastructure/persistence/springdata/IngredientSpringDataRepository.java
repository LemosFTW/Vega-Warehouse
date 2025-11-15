package com.vega.warehouse.infrastructure.persistence.springdata;

import com.vega.warehouse.infrastructure.persistence.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientSpringDataRepository extends JpaRepository<IngredientEntity, Long> {
}
