package com.vega.warehouse.infrastructure.persistence.springdata;

import com.vega.warehouse.infrastructure.persistence.entity.CompartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompartmentSpringDataRepository extends JpaRepository<CompartmentEntity, Long> {

    Optional<CompartmentEntity> findByCode(String code);

    List<CompartmentEntity> findAll();
}

