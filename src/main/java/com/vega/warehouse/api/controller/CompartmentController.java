package com.vega.warehouse.api.controller;

import com.vega.warehouse.api.mapper.CompartmentApiMapper;
import com.vega.warehouse.application.dto.AvailableCompartmentResponse;
import com.vega.warehouse.application.usecase.GetAvailableCompartmentsForSaleUseCase;
import com.vega.warehouse.application.usecase.GetAvailableCompartmentsUseCase;
import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/compartimentos")
@Validated
public class CompartmentController {

    private final GetAvailableCompartmentsUseCase getAvailableCompartmentsUseCase;
    private final GetAvailableCompartmentsForSaleUseCase getAvailableCompartmentsForSaleUseCase;

    public CompartmentController(GetAvailableCompartmentsUseCase getAvailableCompartmentsUseCase,
                                 GetAvailableCompartmentsForSaleUseCase getAvailableCompartmentsForSaleUseCase) {
        this.getAvailableCompartmentsUseCase = getAvailableCompartmentsUseCase;
        this.getAvailableCompartmentsForSaleUseCase = getAvailableCompartmentsForSaleUseCase;
    }

    @GetMapping("/disponiveis")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableCompartmentResponse> getAvailableCompartments(
            @RequestParam(name = "quantidade")
            @NotNull(message = "O parâmetro 'quantidade' é obrigatório")
            @Min(value = 0, message = "A quantidade deve ser maior ou igual a zero")
            BigDecimal quantidade,
            @RequestParam(name = "tipo")
            @NotNull(message = "O parâmetro 'tipo' é obrigatório. Valores aceitos: SECO, LIQUIDO, REFRIGERADO")
            IngredientType tipo) {
        List<Compartment> compartments = getAvailableCompartmentsUseCase.execute(tipo, quantidade);
        return compartments.stream()
                .map(CompartmentApiMapper::toResponse)
                .toList();
    }

    @GetMapping("/disponiveis-para-venda")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableCompartmentResponse> getAvailableCompartmentsForSale(
            @RequestParam(name = "tipo")
            @NotNull(message = "O parâmetro 'tipo' é obrigatório. Valores aceitos: SECO, LIQUIDO, REFRIGERADO")
            IngredientType tipo) {
        List<Compartment> compartments = getAvailableCompartmentsForSaleUseCase.execute(tipo);
        return compartments.stream()
                .map(CompartmentApiMapper::toResponse)
                .toList();
    }
}

