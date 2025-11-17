package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateIngredientUseCase - Testes Unitários")
class CreateIngredientUseCaseTest {

    @Mock
    private IngredientRepositoryPort ingredientRepository;

    @InjectMocks
    private CreateIngredientUseCase useCase;

    private Ingredient existingIngredient;

    @BeforeEach
    void setUp() {
        existingIngredient = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", LocalDateTime.now());
    }

    @Test
    @DisplayName("Deve criar novo ingrediente quando não existe")
    void testExecute_ShouldCreateNewIngredient_WhenNotExists() {
        // Given
        String name = "Açúcar";
        IngredientType type = IngredientType.SECO;
        BigDecimal quantity = new BigDecimal("50");
        String unit = "kg";

        when(ingredientRepository.findbyName(name)).thenReturn(Optional.empty());
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient ingredient = invocation.getArgument(0);
            return new Ingredient(1L, ingredient.getName(), ingredient.getType(), 
                    ingredient.getQuantity(), ingredient.getUnit(), ingredient.getCreatedAt());
        });

        // When
        Ingredient result = useCase.execute(name, type, quantity, unit);

        // Then
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(type, result.getType());
        assertEquals(quantity, result.getQuantity());
        assertEquals(unit, result.getUnit());
        assertNotNull(result.getCreatedAt());

        ArgumentCaptor<Ingredient> captor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(captor.capture());
        Ingredient savedIngredient = captor.getValue();
        assertNull(savedIngredient.getId()); // Novo ingrediente não tem ID ainda
        assertEquals(name, savedIngredient.getName());
    }

    @Test
    @DisplayName("Deve atualizar volume quando ingrediente já existe")
    void testExecute_ShouldUpdateVolume_WhenIngredientExists() {
        // Given
        String name = "Farinha";
        BigDecimal newQuantity = new BigDecimal("50");
        BigDecimal expectedTotal = new BigDecimal("150"); // 100 + 50

        when(ingredientRepository.findbyName(name)).thenReturn(Optional.of(existingIngredient));
        when(ingredientRepository.updateVolume(eq(existingIngredient), eq(newQuantity)))
                .thenAnswer(invocation -> {
                    Ingredient ingredient = invocation.getArgument(0);
                    BigDecimal additionalQuantity = invocation.getArgument(1);
                    ingredient.setQuantity(ingredient.getQuantity().add(additionalQuantity));
                    return ingredient;
                });
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient ingredient = invocation.getArgument(0);
            return ingredient;
        });

        // When
        Ingredient result = useCase.execute(name, IngredientType.SECO, newQuantity, "kg");

        // Then
        assertNotNull(result);
        verify(ingredientRepository).findbyName(name);
        verify(ingredientRepository).updateVolume(eq(existingIngredient), eq(newQuantity));
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    @DisplayName("Deve criar ingrediente do tipo LIQUIDO")
    void testExecute_ShouldCreateLiquidoIngredient() {
        // Given
        String name = "Óleo";
        IngredientType type = IngredientType.LIQUIDO;
        BigDecimal quantity = new BigDecimal("200");
        String unit = "L";

        when(ingredientRepository.findbyName(name)).thenReturn(Optional.empty());
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient ingredient = invocation.getArgument(0);
            return new Ingredient(1L, ingredient.getName(), ingredient.getType(), 
                    ingredient.getQuantity(), ingredient.getUnit(), ingredient.getCreatedAt());
        });

        // When
        Ingredient result = useCase.execute(name, type, quantity, unit);

        // Then
        assertNotNull(result);
        assertEquals(IngredientType.LIQUIDO, result.getType());
        assertEquals("Óleo", result.getName());
    }

    @Test
    @DisplayName("Deve criar ingrediente do tipo REFRIGERADO")
    void testExecute_ShouldCreateRefrigeradoIngredient() {
        // Given
        String name = "Leite";
        IngredientType type = IngredientType.REFRIGERADO;
        BigDecimal quantity = new BigDecimal("150");
        String unit = "kg";

        when(ingredientRepository.findbyName(name)).thenReturn(Optional.empty());
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient ingredient = invocation.getArgument(0);
            return new Ingredient(1L, ingredient.getName(), ingredient.getType(), 
                    ingredient.getQuantity(), ingredient.getUnit(), ingredient.getCreatedAt());
        });

        // When
        Ingredient result = useCase.execute(name, type, quantity, unit);

        // Then
        assertNotNull(result);
        assertEquals(IngredientType.REFRIGERADO, result.getType());
        assertEquals("Leite", result.getName());
    }

    @Test
    @DisplayName("Deve criar ingrediente com quantidade decimal")
    void testExecute_ShouldCreateIngredientWithDecimalQuantity() {
        // Given
        String name = "Sal";
        BigDecimal quantity = new BigDecimal("12.5");
        String unit = "kg";

        when(ingredientRepository.findbyName(name)).thenReturn(Optional.empty());
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient ingredient = invocation.getArgument(0);
            return new Ingredient(1L, ingredient.getName(), ingredient.getType(), 
                    ingredient.getQuantity(), ingredient.getUnit(), ingredient.getCreatedAt());
        });

        // When
        Ingredient result = useCase.execute(name, IngredientType.SECO, quantity, unit);

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("12.5"), result.getQuantity());
    }

    @Test
    @DisplayName("Deve atualizar volume múltiplas vezes quando ingrediente existe")
    void testExecute_ShouldUpdateVolumeMultipleTimes() {
        // Given
        String name = "Farinha";
        BigDecimal firstQuantity = new BigDecimal("50");
        BigDecimal secondQuantity = new BigDecimal("30");

        when(ingredientRepository.findbyName(name)).thenReturn(Optional.of(existingIngredient));
        when(ingredientRepository.updateVolume(any(Ingredient.class), any(BigDecimal.class)))
                .thenAnswer(invocation -> {
                    Ingredient ingredient = invocation.getArgument(0);
                    BigDecimal additionalQuantity = invocation.getArgument(1);
                    ingredient.setQuantity(ingredient.getQuantity().add(additionalQuantity));
                    return ingredient;
                });
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient ingredient = invocation.getArgument(0);
            return ingredient;
        });

        // When - Primeira atualização
        Ingredient result1 = useCase.execute(name, IngredientType.SECO, firstQuantity, "kg");
        
        // Segunda atualização
        Ingredient result2 = useCase.execute(name, IngredientType.SECO, secondQuantity, "kg");

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        verify(ingredientRepository, times(2)).findbyName(name);
        verify(ingredientRepository, times(2)).updateVolume(any(Ingredient.class), any(BigDecimal.class));
        verify(ingredientRepository, times(2)).save(any(Ingredient.class));
    }

    @Test
    @DisplayName("Deve criar ingrediente com data de criação")
    void testExecute_ShouldCreateIngredientWithCreatedAt() {
        // Given
        String name = "Açúcar";
        LocalDateTime beforeExecution = LocalDateTime.now();

        when(ingredientRepository.findbyName(name)).thenReturn(Optional.empty());
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient ingredient = invocation.getArgument(0);
            return new Ingredient(1L, ingredient.getName(), ingredient.getType(), 
                    ingredient.getQuantity(), ingredient.getUnit(), ingredient.getCreatedAt());
        });

        // When
        Ingredient result = useCase.execute(name, IngredientType.SECO, new BigDecimal("50"), "kg");
        LocalDateTime afterExecution = LocalDateTime.now();

        // Then
        assertNotNull(result);
        assertNotNull(result.getCreatedAt());
        assertTrue(result.getCreatedAt().isAfter(beforeExecution.minusSeconds(1)) || 
                   result.getCreatedAt().isEqual(beforeExecution));
        assertTrue(result.getCreatedAt().isBefore(afterExecution.plusSeconds(1)) || 
                   result.getCreatedAt().isEqual(afterExecution));
    }
}

