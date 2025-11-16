package com.vega.warehouse.api.controller;


import com.vega.warehouse.application.dto.MovementLogResponse;
import com.vega.warehouse.api.mapper.MovementApiMapper;
import com.vega.warehouse.application.usecase.ListMovementsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoryController {

    private final ListMovementsUseCase listMovementsUseCase;

    public HistoryController(ListMovementsUseCase listMovementsUseCase) {
        this.listMovementsUseCase = listMovementsUseCase;
    }

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