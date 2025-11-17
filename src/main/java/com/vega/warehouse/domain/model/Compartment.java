package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.service.CapacityService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa um compartimento no armazém.
 * <p>
 * Um compartimento possui um código único, tipo de ingrediente que pode armazenar,
 * capacidade máxima, quantidade atual e data da última mudança de tipo.
 * Implementa regras de negócio para verificar se pode armazenar determinado
 * tipo e quantidade de ingrediente.
 * </p>
 *
 * @author LemosFTW
 */
public class Compartment {

    /** Identificador único do compartimento */
    private Long id;
    
    /** Código único do compartimento */
    private String code;
    
    /** Tipo de ingrediente que o compartimento armazena atualmente */
    private IngredientType type;
    
    /** Capacidade máxima do compartimento */
    private BigDecimal maxCapacity;
    
    /** Quantidade atual armazenada no compartimento */
    private BigDecimal currentQuantity;
    
    /** Data da última mudança de tipo de ingrediente */
    private LocalDate lastTypeChangeDate;

    /**
     * Construtor completo do compartimento.
     *
     * @param id identificador único
     * @param code código único do compartimento
     * @param type tipo de ingrediente armazenado
     * @param maxCapacity capacidade máxima
     * @param currentQuantity quantidade atual armazenada
     * @param lastTypeChangeDate data da última mudança de tipo
     */
    public Compartment(Long id,
                       String code,
                       IngredientType type,
                       BigDecimal maxCapacity,
                       BigDecimal currentQuantity,
                       LocalDate lastTypeChangeDate) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.currentQuantity = currentQuantity;
        this.lastTypeChangeDate = lastTypeChangeDate;
    }

    /**
     * Construtor para criação de novo compartimento (sem ID).
     *
     * @param code código único do compartimento
     * @param type tipo de ingrediente armazenado
     * @param maxCapacity capacidade máxima
     * @param currentQuantity quantidade atual armazenada
     * @param lastTypeChangeDate data da última mudança de tipo
     */
    public Compartment(String code,
                       IngredientType type,
                       BigDecimal maxCapacity,
                       BigDecimal currentQuantity,
                       LocalDate lastTypeChangeDate) {
        this(null, code, type, maxCapacity, currentQuantity, lastTypeChangeDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IngredientType getType() {
        return type;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public BigDecimal getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public BigDecimal getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public LocalDate getLastTypeChangeDate() {
        return lastTypeChangeDate;
    }

    public void setLastTypeChangeDate(LocalDate lastTypeChangeDate) {
        this.lastTypeChangeDate = lastTypeChangeDate;
    }

    /**
     * Calcula o espaço disponível no compartimento.
     *
     * @return espaço disponível (capacidade máxima - quantidade atual)
     */
    public BigDecimal getAvailableSpace() {
        return maxCapacity.subtract(currentQuantity);
    }

    /**
     * Verifica se o compartimento tem espaço suficiente para a quantidade solicitada.
     *
     * @param requiredQuantity quantidade necessária
     * @return true se há espaço suficiente, false caso contrário
     */
    public boolean hasEnoughSpace(BigDecimal requiredQuantity) {
        return getAvailableSpace().compareTo(requiredQuantity) >= 0;
    }

    /**
     * Verifica se o compartimento pode armazenar o tipo de ingrediente solicitado.
     * <p>
     * Regras de negócio:
     * <ul>
     *   <li>Se está vazio, pode armazenar qualquer tipo (desde que tenha capacidade adequada)</li>
     *   <li>Se tem o mesmo tipo, pode armazenar</li>
     *   <li>Se mudou de tipo hoje, não pode armazenar outro tipo até amanhã</li>
     *   <li>Se mudou de tipo antes de hoje, pode armazenar novo tipo (desde que tenha capacidade adequada)</li>
     * </ul>
     * </p>
     *
     * @param requestedType tipo de ingrediente solicitado
     * @return true se pode armazenar o tipo, false caso contrário
     */
    public boolean canStoreType(IngredientType requestedType) {
        // Se está vazio, pode armazenar qualquer tipo (mas precisa ter capacidade correta)
        if (currentQuantity.compareTo(BigDecimal.ZERO) == 0) {
            // Verifica se a capacidade máxima do compartimento é adequada para o tipo solicitado
            BigDecimal requiredCapacity = CapacityService.getMaxCapacityForType(requestedType);
            return maxCapacity.compareTo(requiredCapacity) >= 0;
        }

        // Se tem o mesmo tipo, pode armazenar
        if (type.equals(requestedType)) {
            return true;
        }

        // Se mudou de tipo hoje, não pode armazenar outro tipo até amanhã
        if (lastTypeChangeDate != null && lastTypeChangeDate.equals(LocalDate.now())) {
            return false;
        }

        // Se mudou de tipo antes de hoje, pode armazenar novo tipo (mas precisa ter capacidade correta)
        BigDecimal requiredCapacity = CapacityService.getMaxCapacityForType(requestedType);
        return maxCapacity.compareTo(requiredCapacity) >= 0;
    }

    @Override
    public String toString() {
        return "Compartment{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", maxCapacity=" + maxCapacity +
                ", currentQuantity=" + currentQuantity +
                ", lastTypeChangeDate=" + lastTypeChangeDate +
                '}';
    }
}

