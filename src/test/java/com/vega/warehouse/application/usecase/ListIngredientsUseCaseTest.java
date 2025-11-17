package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListIngredientsUseCase - Testes Unitários")
class ListIngredientsUseCaseTest {

    @Mock
    private IngredientRepositoryPort ingredientRepository;

    @InjectMocks
    private ListIngredientsUseCase useCase;

    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Ingredient ingredient3;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        ingredient1 = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", now);
        ingredient2 = new Ingredient(2L, "Açúcar", IngredientType.SECO, 
                new BigDecimal("50"), "kg", now);
        ingredient3 = new Ingredient(3L, "Óleo", IngredientType.LIQUIDO, 
                new BigDecimal("200"), "L", now);
    }

    @Test
    @DisplayName("Deve retornar lista de todos os ingredientes")
    void testExecute_ShouldReturnAllIngredients() {
        // Given
        List<Ingredient> expectedIngredients = Arrays.asList(ingredient1, ingredient2, ingredient3);
        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        // When
        List<Ingredient> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Farinha", result.get(0).getName());
        assertEquals("Açúcar", result.get(1).getName());
        assertEquals("Óleo", result.get(2).getName());
        verify(ingredientRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há ingredientes")
    void testExecute_ShouldReturnEmptyList_WhenNoIngredients() {
        // Given
        when(ingredientRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Ingredient> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ingredientRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista com um único ingrediente")
    void testExecute_ShouldReturnSingleIngredient() {
        // Given
        List<Ingredient> expectedIngredients = Arrays.asList(ingredient1);
        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        // When
        List<Ingredient> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Farinha", result.get(0).getName());
        assertEquals(IngredientType.SECO, result.get(0).getType());
        verify(ingredientRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar ingredientes de diferentes tipos")
    void testExecute_ShouldReturnIngredientsOfDifferentTypes() {
        // Given
        Ingredient refrigerado = new Ingredient(4L, "Leite", IngredientType.REFRIGERADO, 
                new BigDecimal("150"), "kg", LocalDateTime.now());
        List<Ingredient> expectedIngredients = Arrays.asList(ingredient1, ingredient3, refrigerado);
        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        // When
        List<Ingredient> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(IngredientType.SECO, result.get(0).getType());
        assertEquals(IngredientType.LIQUIDO, result.get(1).getType());
        assertEquals(IngredientType.REFRIGERADO, result.get(2).getType());
        verify(ingredientRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar ingredientes ordenados conforme retornado pelo repositório")
    void testExecute_ShouldReturnIngredientsInRepositoryOrder() {
        // Given
        List<Ingredient> expectedIngredients = Arrays.asList(ingredient2, ingredient1, ingredient3);
        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        // When
        List<Ingredient> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Açúcar", result.get(0).getName());
        assertEquals("Farinha", result.get(1).getName());
        assertEquals("Óleo", result.get(2).getName());
        verify(ingredientRepository).findAll();
    }
}

