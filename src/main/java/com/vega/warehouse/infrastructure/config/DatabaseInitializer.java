package com.vega.warehouse.infrastructure.config;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.service.CapacityService;
import com.vega.warehouse.infrastructure.persistence.entity.CompartmentEntity;
import com.vega.warehouse.infrastructure.persistence.springdata.CompartmentSpringDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initCompartments(CompartmentSpringDataRepository repository) {
        return args -> {

            if (repository.count() == 0) {
                // Criando compartimentos com capacidades máximas adequadas para cada tipo
                // Utilizando CapacityService para obter as capacidades corretas
                // C1: 600kg - pode armazenar SECO (600), LIQUIDO (500) ou REFRIGERADO (400)
                // C2: 500L - pode armazenar LIQUIDO (500) ou REFRIGERADO (400)
                // C3: 400kg - pode armazenar apenas REFRIGERADO (400)
                // C4: 600kg - vazio, pode armazenar qualquer tipo
                // C5: 500L - parcialmente ocupado com LIQUIDO
                List<CompartmentEntity> compartments = List.of(
                        new CompartmentEntity("C1", IngredientType.SECO, 
                                CapacityService.getMaxCapacityForType(IngredientType.SECO), 
                                BigDecimal.ZERO, null),
                        new CompartmentEntity("C2", IngredientType.LIQUIDO, 
                                CapacityService.getMaxCapacityForType(IngredientType.LIQUIDO), 
                                BigDecimal.ZERO, null),
                        new CompartmentEntity("C3", IngredientType.REFRIGERADO, 
                                CapacityService.getMaxCapacityForType(IngredientType.REFRIGERADO), 
                                BigDecimal.ZERO, null),
                        new CompartmentEntity("C4", IngredientType.SECO, 
                                CapacityService.getMaxCapacityForType(IngredientType.SECO), 
                                BigDecimal.ZERO, null),
                        new CompartmentEntity("C5", IngredientType.LIQUIDO, 
                                CapacityService.getMaxCapacityForType(IngredientType.LIQUIDO), 
                                new BigDecimal("200"), null)
                );

                repository.saveAll(compartments);
                System.out.println("Compartments seeded successfully!");
            }
        };
    }
}
