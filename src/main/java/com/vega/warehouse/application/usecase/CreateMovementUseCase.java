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

/**
 * Caso de uso para criação de movimentações de estoque (entrada ou saída).
 * <p>
 * Gerencia as regras de negócio para movimentações:
 * <ul>
 *   <li>Validações de quantidade e existência de ingrediente/compartimento</li>
 *   <li>Atualização de quantidades nos compartimentos</li>
 *   <li>Regras de mudança de tipo de ingrediente nos compartimentos</li>
 *   <li>Registro da movimentação no histórico</li>
 * </ul>
 * </p>
 *
 * @author LemosFTW
 */
@Service
public class CreateMovementUseCase {

    private final IngredientRepositoryPort ingredientRepository;
    private final CompartmentRepositoryPort compartmentRepository;
    private final MovementRepositoryPort movementRepository;

    /**
     * Construtor do caso de uso.
     *
     * @param ingredientRepository repositório de ingredientes
     * @param compartmentRepository repositório de compartimentos
     * @param movementRepository repositório de movimentações
     */
    public CreateMovementUseCase(IngredientRepositoryPort ingredientRepository,
                                 CompartmentRepositoryPort compartmentRepository,
                                 MovementRepositoryPort movementRepository) {
        this.ingredientRepository = ingredientRepository;
        this.compartmentRepository = compartmentRepository;
        this.movementRepository = movementRepository;
    }

    /**
     * Executa a criação de uma movimentação de estoque.
     * <p>
     * Valida os dados, atualiza o compartimento e registra a movimentação.
     * </p>
     *
     * @param movementType tipo de movimentação (ENTRADA ou SAIDA)
     * @param ingredientId ID do ingrediente
     * @param compartmentCode código do compartimento
     * @param quantity quantidade a ser movimentada
     * @param responsible responsável pela movimentação
     * @return movimentação criada
     * @throws IllegalArgumentException se os dados forem inválidos ou as regras de negócio não forem atendidas
     */
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

    /**
     * Processa uma movimentação de entrada no estoque.
     * <p>
     * Valida se o compartimento pode receber o tipo de ingrediente,
     * verifica capacidade disponível e atualiza a quantidade.
     * </p>
     *
     * @param ingredient ingrediente a ser armazenado
     * @param compartment compartimento de destino
     * @param quantity quantidade a ser armazenada
     * @throws IllegalArgumentException se não houver espaço ou se as regras de tipo não forem atendidas
     */
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

    /**
     * Processa uma movimentação de saída do estoque.
     * <p>
     * Valida se o compartimento contém o tipo de ingrediente,
     * verifica se há quantidade suficiente e atualiza a quantidade.
     * </p>
     *
     * @param ingredient ingrediente a ser retirado
     * @param compartment compartimento de origem
     * @param quantity quantidade a ser retirada
     * @throws IllegalArgumentException se não houver quantidade suficiente ou se o tipo não corresponder
     */
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
