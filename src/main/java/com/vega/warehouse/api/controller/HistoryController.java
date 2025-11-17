package com.vega.warehouse.api.controller;


import com.vega.warehouse.application.dto.MovementLogResponse;
import com.vega.warehouse.api.mapper.MovementApiMapper;
import com.vega.warehouse.application.usecase.ListMovementsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para consulta do histórico de movimentações.
 * <p>
 * Expõe endpoint para listar todas as movimentações de estoque
 * com opções de ordenação por data ou compartimento.
 * </p>
 *
 * @author LemosFTW
 */
@RestController
@RequestMapping("/historico")
public class HistoryController {

    private final ListMovementsUseCase listMovementsUseCase;

    /**
     * Construtor do controller.
     *
     * @param listMovementsUseCase caso de uso para listagem de movimentações
     */
    public HistoryController(ListMovementsUseCase listMovementsUseCase) {
        this.listMovementsUseCase = listMovementsUseCase;
    }

    /**
     * Lista todas as movimentações de estoque com ordenação opcional.
     *
     * @param sortBy critério de ordenação ("date" ou "compartment"), padrão: "date"
     * @param order direção da ordenação ("asc" ou "desc"), padrão: "desc"
     * @return lista de movimentações ordenadas
     */
    @GetMapping
    public List<MovementLogResponse> list(
            @RequestParam(name = "sortBy", required = false, defaultValue = "date") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "desc") String order
    ) {
        return listMovementsUseCase.execute(sortBy, order)
                .stream()
                .map(MovementApiMapper::toResponse)
                .toList();
    }
}