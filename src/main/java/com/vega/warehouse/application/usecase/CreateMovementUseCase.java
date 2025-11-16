package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.MovementType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import com.vega.warehouse.domain.repository.MovementRepositoryPort;
import com.vega.warehouse.domain.service.CapacityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CreateMovementUseCase {

    private final IngredientRepositoryPort ingredientRepository;
    private final CompartmentRepositoryPort compartmentRepository;
    private final MovementRepositoryPort movementRepository;

    public CreateMovementUseCase(IngredientRepositoryPort ingredientRepository,
                                 CompartmentRepositoryPort compartmentRepository,
                                 MovementRepositoryPort movementRepository) {
        this.ingredientRepository = ingredientRepository;
        this.compartmentRepository = compartmentRepository;
        this.movementRepository = movementRepository;
    }

    @Transactional
    public Movement execute(MovementType movementType,
                            Long ingredientId,
                            String compartmentCode,
                            BigDecimal quantity,
                            String responsible) {

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found."));

        Compartment compartment = compartmentRepository.findByCode(compartmentCode)
                .orElseThrow(() -> new IllegalArgumentException("Compartment not found."));

        switch (movementType) {
            case ENTRADA -> handleEntrada(ingredient, compartment, quantity);
            case SAIDA   -> handleSaida(ingredient, compartment, quantity);
            default      -> throw new IllegalArgumentException("Invalid movement type.");
        }

        // salva o compartimento com a nova quantidade / tipo
        Compartment updatedCompartment = compartmentRepository.save(compartment);

        Movement movement = new Movement(
                null,
                movementType,
                ingredient.getName(),
                updatedCompartment.getCode(),
                quantity,
                ingredient.getType(),
                responsible,
                LocalDateTime.now()
        );

        return movementRepository.save(movement);
    }

    private void handleEntrada(Ingredient ingredient,
                               Compartment compartment,
                               BigDecimal quantity) {

        if (compartment.getType() == null) {
            compartment.setType(ingredient.getType());
            compartment.setMaxCapacity(CapacityService.getMaxCapacityForType(ingredient.getType()));
            compartment.setLastTypeChangeDate(LocalDate.now());
        } else if (compartment.getType() != ingredient.getType()) {
            // se já tem tipo diferente, só pode trocar se:
            // - currentQuantity == 0
            // - lastTypeChangeDate < hoje
            if (compartment.getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0) {
                throw new IllegalArgumentException(
                        "Compartment is occupied with another ingredient type.");
            }
            if (compartment.getLastTypeChangeDate() != null &&
                    !compartment.getLastTypeChangeDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException(
                        "Compartment type can only change the day after the last storage.");
            }

            compartment.setType(ingredient.getType());
            compartment.setMaxCapacity(CapacityService.getMaxCapacityForType(ingredient.getType()));
            compartment.setLastTypeChangeDate(LocalDate.now());
        }

        BigDecimal newQuantity = compartment.getCurrentQuantity().add(quantity);
        BigDecimal maxCapacity = CapacityService.getMaxCapacityForType(compartment.getType());

        if (newQuantity.compareTo(maxCapacity) > 0) {
            throw new IllegalArgumentException(
                    "Not enough space in compartment for this quantity.");
        }

        compartment.setCurrentQuantity(newQuantity);
    }

    private void handleSaida(Ingredient ingredient,
                             Compartment compartment,
                             BigDecimal quantity) {

        if (compartment.getType() == null) {
            throw new IllegalArgumentException("Compartment is empty.");
        }

        if (compartment.getType() != ingredient.getType()) {
            throw new IllegalArgumentException(
                    "Compartment type does not match ingredient type.");
        }

        if (compartment.getCurrentQuantity().compareTo(quantity) < 0) {
            throw new IllegalArgumentException(
                    "Not enough quantity in compartment for this withdrawal.");
        }

        BigDecimal newQuantity = compartment.getCurrentQuantity().subtract(quantity);
        compartment.setCurrentQuantity(newQuantity);
    }
}
