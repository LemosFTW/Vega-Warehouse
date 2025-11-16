package com.vega.warehouse.api.controller;

import com.vega.warehouse.application.dto.CreateMovementRequest;
import com.vega.warehouse.application.dto.MovementLogResponse;
import com.vega.warehouse.api.dto.response.ApiResponse;
import com.vega.warehouse.api.mapper.MovementApiMapper;
import com.vega.warehouse.application.usecase.CreateMovementUseCase;
import com.vega.warehouse.application.usecase.ListMovementsUseCase;
import com.vega.warehouse.domain.model.Movement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/movements")
public class MovementController {

    private final CreateMovementUseCase createMovementUseCase;

    public MovementController(CreateMovementUseCase createMovementUseCase) {
        this.createMovementUseCase = createMovementUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MovementLogResponse> create(@Valid @RequestBody CreateMovementRequest request) {
        Movement movement = createMovementUseCase.execute(
                request.getType(),
                request.getIngredientId(),
                request.getCompartmentCode(),
                request.getQuantity(),
                request.getResponsible()
        );

        return new ApiResponse<>(
                "Movement created successfully",
                MovementApiMapper.toResponse(movement)
        );
    }
}