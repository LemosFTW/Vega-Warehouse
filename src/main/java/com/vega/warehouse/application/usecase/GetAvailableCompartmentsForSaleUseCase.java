package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso para buscar compartimentos disponíveis para venda.
 * <p>
 * Retorna compartimentos que contêm o tipo de ingrediente especificado
 * e possuem quantidade disponível para venda.
 * </p>
 *
 * @author LemosFTW
 */
@Service
public class GetAvailableCompartmentsForSaleUseCase {

    private final CompartmentRepositoryPort compartmentRepository;

    /**
     * Construtor do caso de uso.
     *
     * @param compartmentRepository repositório de compartimentos
     */
    public GetAvailableCompartmentsForSaleUseCase(CompartmentRepositoryPort compartmentRepository) {
        this.compartmentRepository = compartmentRepository;
    }

    /**
     * Executa a busca de compartimentos disponíveis para venda.
     *
     * @param type tipo de ingrediente
     * @return lista de compartimentos disponíveis para venda
     */
    public List<Compartment> execute(IngredientType type) {
        return compartmentRepository.findAvailableForSaleByType(type);
    }
}

