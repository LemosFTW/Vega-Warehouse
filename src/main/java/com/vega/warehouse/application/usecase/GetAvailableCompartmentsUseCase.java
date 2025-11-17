package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Caso de uso para buscar compartimentos disponíveis para armazenamento.
 * <p>
 * Retorna compartimentos que podem armazenar o tipo e quantidade
 * de ingrediente especificados.
 * </p>
 *
 * @author LemosFTW
 */
@Service
public class GetAvailableCompartmentsUseCase {

    private final CompartmentRepositoryPort compartmentRepository;

    /**
     * Construtor do caso de uso.
     *
     * @param compartmentRepository repositório de compartimentos
     */
    public GetAvailableCompartmentsUseCase(CompartmentRepositoryPort compartmentRepository) {
        this.compartmentRepository = compartmentRepository;
    }

    /**
     * Executa a busca de compartimentos disponíveis.
     *
     * @param type tipo de ingrediente
     * @param quantity quantidade necessária
     * @return lista de compartimentos disponíveis
     */
    public List<Compartment> execute(IngredientType type, BigDecimal quantity) {
        return compartmentRepository.findAvailableForStorage(type, quantity);
    }
}

