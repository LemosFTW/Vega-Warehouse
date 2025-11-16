package com.vega.warehouse.infrastructure.persistence.springdata;

import com.vega.warehouse.infrastructure.persistence.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientSpringDataRepository extends JpaRepository<IngredientEntity, Long> {
    Optional<IngredientEntity> findByName(String name);

    @Query("""
            select i.type as type, sum(i.quantity) as totalQuantity
            from IngredientEntity i
            group by i.type
            """)
    List<IngredientVolumeByTypeProjection> getTotalVolumeByType();
}
