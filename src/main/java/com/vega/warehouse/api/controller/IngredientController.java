package com.vega.warehouse.api.controller;

import com.vega.warehouse.api.dto.request.CreateIngredientRequest;
import com.vega.warehouse.api.dto.response.IngredientResponse;
import com.vega.warehouse.api.mapper.IngredientApiMapper;
import com.vega.warehouse.application.usecase.CreateIngredientUseCase;
import com.vega.warehouse.application.usecase.ListIngredientsUseCase;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.api.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final CreateIngredientUseCase createIngredientUseCase;
    private final ListIngredientsUseCase listIngredientsUseCase;

    public IngredientController(CreateIngredientUseCase createIngredientUseCase,
                                ListIngredientsUseCase listIngredientsUseCase) {
        this.createIngredientUseCase = createIngredientUseCase;
        this.listIngredientsUseCase = listIngredientsUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<IngredientResponse> create(@Valid @RequestBody CreateIngredientRequest request) {
        Ingredient ingredient = createIngredientUseCase.execute(
                request.getName(),
                request.getType(),
                request.getQuantity(),
                request.getUnit()
        );
        return new ApiResponse<>(
                "Ingredient created successfully",
                IngredientApiMapper.toResponse(ingredient)
        );
    }

    @GetMapping
    public List<IngredientResponse> list() {
        return listIngredientsUseCase.execute()
                .stream()
                .map(IngredientApiMapper::toResponse)
                .toList();
    }
}
