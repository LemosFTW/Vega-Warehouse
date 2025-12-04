package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.enums.MovementType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import com.vega.warehouse.domain.repository.MovementRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateMovementUseCase - Testes Unitários")
class CreateMovementUseCaseTest {

    @Mock
    private IngredientRepositoryPort ingredientRepository;

    @Mock
    private CompartmentRepositoryPort compartmentRepository;

    @Mock
    private MovementRepositoryPort movementRepository;

    @InjectMocks
    private CreateMovementUseCase useCase;

    private Ingredient ingredient;
    private Compartment compartment;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", LocalDateTime.now(),0);
        
        compartment = new Compartment(1L, "C1", null, 
                BigDecimal.ZERO, BigDecimal.ZERO, null);
    }

    @Test
    @DisplayName("Deve criar movimento de ENTRADA em compartimento vazio")
    void testExecute_Entrada_EmptyCompartment() {
        // Given
        MovementType movementType = MovementType.ENTRADA;
        BigDecimal quantity = new BigDecimal("100");
        String responsible = "João Silva";
        BigDecimal initialIngredientQuantity = ingredient.getQuantity();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(compartment));
        when(compartmentRepository.save(any(Compartment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> {
            Movement m = invocation.getArgument(0);
            return new Movement(1L, m.getType(), m.getIngredientName(), 
                    m.getCompartmentCode(), m.getQuantity(), m.getIngredientType(), 
                    m.getResponsible(), m.getMovementDateTime());
        });

        // When
        Movement result = useCase.execute(movementType, 1L, "C1", quantity, responsible);

        // Then
        assertNotNull(result);
        assertEquals(MovementType.ENTRADA, result.getType());
        assertEquals("Farinha", result.getIngredientName());
        assertEquals("C1", result.getCompartmentCode());
        assertEquals(quantity, result.getQuantity());
        assertEquals(responsible, result.getResponsible());
        assertEquals(IngredientType.SECO, result.getIngredientType());

        // Verificar que o compartimento foi atualizado
        ArgumentCaptor<Compartment> compartmentCaptor = ArgumentCaptor.forClass(Compartment.class);
        verify(compartmentRepository).save(compartmentCaptor.capture());
        Compartment savedCompartment = compartmentCaptor.getValue();
        assertEquals(IngredientType.SECO, savedCompartment.getType());
        assertEquals(new BigDecimal("100"), savedCompartment.getCurrentQuantity());
        assertEquals(new BigDecimal("600"), savedCompartment.getMaxCapacity());
        assertEquals(LocalDate.now(), savedCompartment.getLastTypeChangeDate());

        // Verificar que a quantidade do ingrediente foi decrementada
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(ingredientCaptor.capture());
        Ingredient savedIngredient = ingredientCaptor.getValue();
        BigDecimal expectedQuantity = initialIngredientQuantity.subtract(savedCompartment.getCurrentQuantity());
        assertEquals(expectedQuantity, savedIngredient.getQuantity());
    }

    @Test
    @DisplayName("Deve criar movimento de ENTRADA em compartimento com mesmo tipo")
    void testExecute_Entrada_SameType() {
        // Given
        Compartment occupiedCompartment = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("200"), LocalDate.now().minusDays(1));
        
        MovementType movementType = MovementType.ENTRADA;
        BigDecimal quantity = new BigDecimal("100");
        String responsible = "Maria Santos";
        BigDecimal initialIngredientQuantity = ingredient.getQuantity();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(occupiedCompartment));
        when(compartmentRepository.save(any(Compartment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> {
            Movement m = invocation.getArgument(0);
            return new Movement(1L, m.getType(), m.getIngredientName(), 
                    m.getCompartmentCode(), m.getQuantity(), m.getIngredientType(), 
                    m.getResponsible(), m.getMovementDateTime());
        });

        // When
        Movement result = useCase.execute(movementType, 1L, "C1", quantity, responsible);

        // Then
        assertNotNull(result);
        
        ArgumentCaptor<Compartment> compartmentCaptor = ArgumentCaptor.forClass(Compartment.class);
        verify(compartmentRepository).save(compartmentCaptor.capture());
        Compartment savedCompartment = compartmentCaptor.getValue();
        assertEquals(new BigDecimal("300"), savedCompartment.getCurrentQuantity());

        // Verificar que a quantidade do ingrediente foi decrementada
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(ingredientCaptor.capture());
        Ingredient savedIngredient = ingredientCaptor.getValue();
        BigDecimal expectedQuantity = initialIngredientQuantity.subtract(savedCompartment.getCurrentQuantity());
        assertEquals(expectedQuantity, savedIngredient.getQuantity());
    }

    @Test
    @DisplayName("Deve criar movimento de SAIDA e reduzir quantidade do compartimento")
    void testExecute_Saida_ShouldReduceQuantity() {
        // Given
        Compartment occupiedCompartment = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("300"), LocalDate.now());
        
        MovementType movementType = MovementType.SAIDA;
        BigDecimal quantity = new BigDecimal("100");
        String responsible = "Pedro Oliveira";
        BigDecimal initialIngredientQuantity = ingredient.getQuantity();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(occupiedCompartment));
        when(compartmentRepository.save(any(Compartment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> {
            Movement m = invocation.getArgument(0);
            return new Movement(1L, m.getType(), m.getIngredientName(), 
                    m.getCompartmentCode(), m.getQuantity(), m.getIngredientType(), 
                    m.getResponsible(), m.getMovementDateTime());
        });

        // When
        Movement result = useCase.execute(movementType, 1L, "C1", quantity, responsible);

        // Then
        assertNotNull(result);
        assertEquals(MovementType.SAIDA, result.getType());
        
        ArgumentCaptor<Compartment> compartmentCaptor = ArgumentCaptor.forClass(Compartment.class);
        verify(compartmentRepository).save(compartmentCaptor.capture());
        Compartment savedCompartment = compartmentCaptor.getValue();
        assertEquals(new BigDecimal("200"), savedCompartment.getCurrentQuantity());

        // Verificar que a quantidade do ingrediente foi incrementada
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(ingredientCaptor.capture());
        Ingredient savedIngredient = ingredientCaptor.getValue();
        BigDecimal expectedQuantity = initialIngredientQuantity.add(quantity);
        assertEquals(expectedQuantity, savedIngredient.getQuantity());
    }

    @Test
    @DisplayName("Deve lançar exceção quando quantidade é zero ou negativa")
    void testExecute_ShouldThrowException_WhenQuantityIsZeroOrNegative() {
        // Given
        BigDecimal invalidQuantity = BigDecimal.ZERO;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.ENTRADA, 1L, "C1", invalidQuantity, "Responsible"));
        
        assertEquals("Quantity must be greater than zero.", exception.getMessage());
        verify(ingredientRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ingrediente não é encontrado")
    void testExecute_ShouldThrowException_WhenIngredientNotFound() {
        // Given
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.ENTRADA, 1L, "C1", new BigDecimal("100"), "Responsible"));
        
        assertEquals("Ingredient not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando compartimento não é encontrado")
    void testExecute_ShouldThrowException_WhenCompartmentNotFound() {
        // Given
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.ENTRADA, 1L, "C1", new BigDecimal("100"), "Responsible"));
        
        assertEquals("Compartment not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não há espaço suficiente para ENTRADA")
    void testExecute_Entrada_ShouldThrowException_WhenNotEnoughSpace() {
        // Given
        Compartment fullCompartment = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("550"), LocalDate.now());
        
        BigDecimal quantity = new BigDecimal("100"); // Total seria 650, excede 600

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(fullCompartment));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.ENTRADA, 1L, "C1", quantity, "Responsible"));
        
        assertEquals("Not enough space in compartment for this quantity.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não há quantidade suficiente para SAIDA")
    void testExecute_Saida_ShouldThrowException_WhenNotEnoughQuantity() {
        // Given
        Compartment lowQuantityCompartment = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("50"), LocalDate.now());
        
        BigDecimal quantity = new BigDecimal("100"); // Tenta retirar mais do que tem

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(lowQuantityCompartment));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.SAIDA, 1L, "C1", quantity, "Responsible"));
        
        assertEquals("Not enough quantity in compartment for this withdrawal.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando compartimento está ocupado com tipo diferente")
    void testExecute_Entrada_ShouldThrowException_WhenCompartmentOccupiedWithDifferentType() {
        // Given
        Compartment occupiedCompartment = new Compartment(1L, "C1", IngredientType.LIQUIDO, 
                new BigDecimal("500"), new BigDecimal("100"), LocalDate.now());

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(occupiedCompartment));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.ENTRADA, 1L, "C1", new BigDecimal("100"), "Responsible"));
        
        assertEquals("Compartment is occupied with another ingredient type.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tenta SAIDA de compartimento vazio")
    void testExecute_Saida_ShouldThrowException_WhenCompartmentEmpty() {
        // Given
        Compartment emptyCompartment = new Compartment(1L, "C1", null, 
                BigDecimal.ZERO, BigDecimal.ZERO, null);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(emptyCompartment));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.SAIDA, 1L, "C1", new BigDecimal("100"), "Responsible"));
        
        assertEquals("Compartment is empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tipo de ingrediente não corresponde ao compartimento na SAIDA")
    void testExecute_Saida_ShouldThrowException_WhenTypeMismatch() {
        // Given
        Compartment differentTypeCompartment = new Compartment(1L, "C1", IngredientType.LIQUIDO, 
                new BigDecimal("500"), new BigDecimal("100"), LocalDate.now());

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(differentTypeCompartment));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.SAIDA, 1L, "C1", new BigDecimal("50"), "Responsible"));
        
        assertEquals("Compartment type does not match ingredient type.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir ENTRADA quando compartimento mudou de tipo antes de hoje")
    void testExecute_Entrada_ShouldAllow_WhenTypeChangedBeforeToday() {
        // Given
        Compartment changedTypeCompartment = new Compartment(1L, "C1", IngredientType.LIQUIDO, 
                new BigDecimal("500"), BigDecimal.ZERO, LocalDate.now().minusDays(1));
        
        BigDecimal quantity = new BigDecimal("100");
        String responsible = "Ana Costa";
        BigDecimal initialIngredientQuantity = ingredient.getQuantity();

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(changedTypeCompartment));
        when(compartmentRepository.save(any(Compartment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> {
            Movement m = invocation.getArgument(0);
            return new Movement(1L, m.getType(), m.getIngredientName(), 
                    m.getCompartmentCode(), m.getQuantity(), m.getIngredientType(), 
                    m.getResponsible(), m.getMovementDateTime());
        });

        // When
        Movement result = useCase.execute(MovementType.ENTRADA, 1L, "C1", quantity, responsible);

        // Then
        assertNotNull(result);
        ArgumentCaptor<Compartment> compartmentCaptor = ArgumentCaptor.forClass(Compartment.class);
        verify(compartmentRepository).save(compartmentCaptor.capture());
        Compartment savedCompartment = compartmentCaptor.getValue();
        assertEquals(IngredientType.SECO, savedCompartment.getType());
        assertEquals(new BigDecimal("600"), savedCompartment.getMaxCapacity());

        // Verificar que a quantidade do ingrediente foi decrementada
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(ingredientCaptor.capture());
        Ingredient savedIngredient = ingredientCaptor.getValue();
        BigDecimal expectedQuantity = initialIngredientQuantity.subtract(savedCompartment.getCurrentQuantity());
        assertEquals(expectedQuantity, savedIngredient.getQuantity());
    }

    @Test
    @DisplayName("Deve decrementar quantidade do ingrediente em movimento de ENTRADA")
    void testExecute_Entrada_ShouldDecrementIngredientQuantity() {
        // Given
        BigDecimal initialIngredientQuantity = new BigDecimal("500");
        Ingredient testIngredient = new Ingredient(1L, "Açúcar", IngredientType.SECO, 
                initialIngredientQuantity, "kg", LocalDateTime.now(),0);
        
        Compartment emptyCompartment = new Compartment(1L, "C1", null, 
                BigDecimal.ZERO, BigDecimal.ZERO, null);
        
        BigDecimal movementQuantity = new BigDecimal("150");
        String responsible = "Test User";

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(testIngredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(emptyCompartment));
        when(compartmentRepository.save(any(Compartment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> {
            Movement m = invocation.getArgument(0);
            return new Movement(1L, m.getType(), m.getIngredientName(), 
                    m.getCompartmentCode(), m.getQuantity(), m.getIngredientType(), 
                    m.getResponsible(), m.getMovementDateTime());
        });

        // When
        useCase.execute(MovementType.ENTRADA, 1L, "C1", movementQuantity, responsible);

        // Then
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(ingredientCaptor.capture());
        Ingredient savedIngredient = ingredientCaptor.getValue();
        
        // A quantidade do ingrediente deve ser decrementada pela quantidade total do compartimento após a entrada
        ArgumentCaptor<Compartment> compartmentCaptor = ArgumentCaptor.forClass(Compartment.class);
        verify(compartmentRepository).save(compartmentCaptor.capture());
        Compartment savedCompartment = compartmentCaptor.getValue();
        
        BigDecimal expectedQuantity = initialIngredientQuantity.subtract(savedCompartment.getCurrentQuantity());
        assertEquals(expectedQuantity, savedIngredient.getQuantity());
        assertTrue(savedIngredient.getQuantity().compareTo(initialIngredientQuantity) < 0, 
                "A quantidade do ingrediente deve ser menor após a entrada");
    }

    @Test
    @DisplayName("Deve incrementar quantidade do ingrediente em movimento de SAIDA")
    void testExecute_Saida_ShouldIncrementIngredientQuantity() {
        // Given
        BigDecimal initialIngredientQuantity = new BigDecimal("200");
        Ingredient testIngredient = new Ingredient(1L, "Sal", IngredientType.SECO, 
                initialIngredientQuantity, "kg", LocalDateTime.now(),0);
        
        Compartment occupiedCompartment = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("250"), LocalDate.now());
        
        BigDecimal movementQuantity = new BigDecimal("80");
        String responsible = "Test User";

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(testIngredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(occupiedCompartment));
        when(compartmentRepository.save(any(Compartment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(movementRepository.save(any(Movement.class))).thenAnswer(invocation -> {
            Movement m = invocation.getArgument(0);
            return new Movement(1L, m.getType(), m.getIngredientName(), 
                    m.getCompartmentCode(), m.getQuantity(), m.getIngredientType(), 
                    m.getResponsible(), m.getMovementDateTime());
        });

        // When
        useCase.execute(MovementType.SAIDA, 1L, "C1", movementQuantity, responsible);

        // Then
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(ingredientCaptor.capture());
        Ingredient savedIngredient = ingredientCaptor.getValue();
        
        // A quantidade do ingrediente deve ser incrementada pela quantidade movimentada
        BigDecimal expectedQuantity = initialIngredientQuantity.add(movementQuantity);
        assertEquals(expectedQuantity, savedIngredient.getQuantity());
        assertTrue(savedIngredient.getQuantity().compareTo(initialIngredientQuantity) > 0, 
                "A quantidade do ingrediente deve ser maior após a saída");
    }

    @Test
    @DisplayName("Deve validar que quantidade movimentada não excede quantidade do ingrediente em ENTRADA")
    void testExecute_Entrada_ShouldThrowException_WhenQuantityExceedsIngredientQuantity() {
        // Given
        BigDecimal ingredientQuantity = new BigDecimal("50");
        Ingredient testIngredient = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                ingredientQuantity, "kg", LocalDateTime.now(),0);
        
        Compartment emptyCompartment = new Compartment(1L, "C1", null, 
                BigDecimal.ZERO, BigDecimal.ZERO, null);
        
        BigDecimal movementQuantity = new BigDecimal("100"); // Maior que a quantidade do ingrediente

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(testIngredient));
        when(compartmentRepository.findByCode("C1")).thenReturn(Optional.of(emptyCompartment));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> useCase.execute(MovementType.ENTRADA, 1L, "C1", movementQuantity, "Responsible"));
        
        assertEquals("Quantity moved must be lower or equal to the ingredients quantity.", exception.getMessage());
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }
}

