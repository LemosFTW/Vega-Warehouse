package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.enums.MovementSortBy;
import com.vega.warehouse.domain.enums.MovementType;
import com.vega.warehouse.domain.enums.SortDirection;
import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.domain.repository.MovementRepositoryPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListMovementsUseCase - Testes Unitários")
class ListMovementsUseCaseTest {

    @Mock
    private MovementRepositoryPort movementRepository;

    @InjectMocks
    private ListMovementsUseCase useCase;

    private Movement movement1;
    private Movement movement2;
    private Movement movement3;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        movement1 = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João", now);
        movement2 = new Movement(2L, MovementType.SAIDA, "Açúcar", "C2", 
                new BigDecimal("50"), IngredientType.SECO, "Maria", now.plusHours(1));
        movement3 = new Movement(3L, MovementType.ENTRADA, "Óleo", "C3", 
                new BigDecimal("200"), IngredientType.LIQUIDO, "Pedro", now.plusHours(2));
    }

    @Test
    @DisplayName("Deve retornar lista de movimentos ordenados por data DESC (padrão)")
    void testExecute_ShouldReturnMovementsSortedByDateDesc_Default() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement3, movement2, movement1);
        when(movementRepository.findAllSorted(eq(MovementSortBy.DATE), eq(SortDirection.DESC)))
                .thenReturn(expectedMovements);

        // When
        List<Movement> result = useCase.execute(null, null);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.DESC);
    }

    @Test
    @DisplayName("Deve retornar movimentos ordenados por data ASC")
    void testExecute_ShouldReturnMovementsSortedByDateAsc() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement1, movement2, movement3);
        when(movementRepository.findAllSorted(eq(MovementSortBy.DATE), eq(SortDirection.ASC)))
                .thenReturn(expectedMovements);

        // When
        List<Movement> result = useCase.execute("date", "asc");

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.ASC);
    }

    @Test
    @DisplayName("Deve retornar movimentos ordenados por data DESC")
    void testExecute_ShouldReturnMovementsSortedByDateDesc() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement3, movement2, movement1);
        when(movementRepository.findAllSorted(eq(MovementSortBy.DATE), eq(SortDirection.DESC)))
                .thenReturn(expectedMovements);

        // When
        List<Movement> result = useCase.execute("date", "desc");

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.DESC);
    }

    @Test
    @DisplayName("Deve retornar movimentos ordenados por compartimento ASC")
    void testExecute_ShouldReturnMovementsSortedByCompartmentAsc() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement1, movement2, movement3);
        when(movementRepository.findAllSorted(eq(MovementSortBy.COMPARTMENT), eq(SortDirection.ASC)))
                .thenReturn(expectedMovements);

        // When
        List<Movement> result = useCase.execute("compartment", "asc");

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.COMPARTMENT, SortDirection.ASC);
    }

    @Test
    @DisplayName("Deve retornar movimentos ordenados por compartimento DESC")
    void testExecute_ShouldReturnMovementsSortedByCompartmentDesc() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement3, movement2, movement1);
        when(movementRepository.findAllSorted(eq(MovementSortBy.COMPARTMENT), eq(SortDirection.DESC)))
                .thenReturn(expectedMovements);

        // When
        List<Movement> result = useCase.execute("compartment", "desc");

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.COMPARTMENT, SortDirection.DESC);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há movimentos")
    void testExecute_ShouldReturnEmptyList_WhenNoMovements() {
        // Given
        when(movementRepository.findAllSorted(any(MovementSortBy.class), any(SortDirection.class)))
                .thenReturn(Collections.emptyList());

        // When
        List<Movement> result = useCase.execute("date", "desc");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.DESC);
    }

    @Test
    @DisplayName("Deve usar valores padrão quando parâmetros são inválidos")
    void testExecute_ShouldUseDefaultValues_WhenInvalidParams() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement1);
        when(movementRepository.findAllSorted(eq(MovementSortBy.DATE), eq(SortDirection.DESC)))
                .thenReturn(expectedMovements);

        // When - parâmetros inválidos
        List<Movement> result = useCase.execute("invalid", "invalid");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.DESC);
    }

    @Test
    @DisplayName("Deve usar valores padrão quando parâmetros são vazios")
    void testExecute_ShouldUseDefaultValues_WhenEmptyParams() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement1);
        when(movementRepository.findAllSorted(eq(MovementSortBy.DATE), eq(SortDirection.DESC)))
                .thenReturn(expectedMovements);

        // When - parâmetros vazios
        List<Movement> result = useCase.execute("", "");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.DESC);
    }

    @Test
    @DisplayName("Deve ser case-insensitive para sortBy")
    void testExecute_ShouldBeCaseInsensitive_ForSortBy() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement1);
        when(movementRepository.findAllSorted(eq(MovementSortBy.DATE), eq(SortDirection.DESC)))
                .thenReturn(expectedMovements);

        // When - maiúsculas
        List<Movement> result = useCase.execute("DATE", "DESC");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.DESC);
    }

    @Test
    @DisplayName("Deve ser case-insensitive para order")
    void testExecute_ShouldBeCaseInsensitive_ForOrder() {
        // Given
        List<Movement> expectedMovements = Arrays.asList(movement1);
        when(movementRepository.findAllSorted(eq(MovementSortBy.DATE), eq(SortDirection.ASC)))
                .thenReturn(expectedMovements);

        // When - maiúsculas
        List<Movement> result = useCase.execute("date", "ASC");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movementRepository).findAllSorted(MovementSortBy.DATE, SortDirection.ASC);
    }
}

