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

/**
 * Controller REST para operações relacionadas a compartimentos.
 * <p>
 * Expõe endpoints para consulta de compartimentos disponíveis
 * para armazenamento e para venda.
 * </p>
 *
 * @author LemosFTW
 */
@RestController
@RequestMapping("/compartimentos")
@Validated
public class CompartmentController {

    private final GetAvailableCompartmentsUseCase getAvailableCompartmentsUseCase;
    private final GetAvailableCompartmentsForSaleUseCase getAvailableCompartmentsForSaleUseCase;

    /**
     * Construtor do controller.
     *
     * @param getAvailableCompartmentsUseCase caso de uso para buscar compartimentos disponíveis
     * @param getAvailableCompartmentsForSaleUseCase caso de uso para buscar compartimentos para venda
     */
    public CompartmentController(GetAvailableCompartmentsUseCase getAvailableCompartmentsUseCase,
                                 GetAvailableCompartmentsForSaleUseCase getAvailableCompartmentsForSaleUseCase) {
        this.getAvailableCompartmentsUseCase = getAvailableCompartmentsUseCase;
        this.getAvailableCompartmentsForSaleUseCase = getAvailableCompartmentsForSaleUseCase;
    }

    /**
     * Busca compartimentos disponíveis para armazenar um tipo e quantidade específicos.
     *
     * @param quantidade quantidade necessária
     * @param tipo tipo de ingrediente
     * @return lista de compartimentos disponíveis
     */
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

    /**
     * Busca compartimentos disponíveis para venda de um tipo específico.
     *
     * @param tipo tipo de ingrediente
     * @return lista de compartimentos disponíveis para venda
     */
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

