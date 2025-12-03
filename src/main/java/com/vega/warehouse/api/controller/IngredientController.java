package com.vega.warehouse.api.controller;

import com.vega.warehouse.api.dto.request.CreateIngredientRequest;
import com.vega.warehouse.api.dto.response.IngredientResponse;
import com.vega.warehouse.api.mapper.IngredientApiMapper;
import com.vega.warehouse.application.dto.IngredientVolumeByTypeResponse;
import com.vega.warehouse.application.usecase.CreateIngredientUseCase;
import com.vega.warehouse.application.usecase.GetTotalVolumeByIngredientTypeUseCase;
import com.vega.warehouse.application.usecase.ListIngredientsUseCase;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.api.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações relacionadas a ingredientes.
 * <p>
 * Expõe endpoints para criação, listagem e consulta de volumes
 * de ingredientes no armazém.
 * </p>
 *
 * @author LemosFTW
 */
@RestController
@RequestMapping("/ingredientes")
public class IngredientController {

    private final CreateIngredientUseCase createIngredientUseCase;
    private final ListIngredientsUseCase listIngredientsUseCase;
    private final GetTotalVolumeByIngredientTypeUseCase getTotalVolumeByIngredientTypeUseCase;

    /**
     * Construtor do controller.
     *
     * @param createIngredientUseCase caso de uso para criação de ingredientes
     * @param listIngredientsUseCase caso de uso para listagem de ingredientes
     * @param getTotalVolumeByIngredientTypeUseCase caso de uso para volume por tipo
     */
    public IngredientController(CreateIngredientUseCase createIngredientUseCase,
                                ListIngredientsUseCase listIngredientsUseCase,
                                GetTotalVolumeByIngredientTypeUseCase getTotalVolumeByIngredientTypeUseCase) {
        this.createIngredientUseCase = createIngredientUseCase;
        this.listIngredientsUseCase = listIngredientsUseCase;
        this.getTotalVolumeByIngredientTypeUseCase = getTotalVolumeByIngredientTypeUseCase;
    }

    /**
     * Cria um novo ingrediente ou atualiza a quantidade se já existir.
     *
     * @param request dados do ingrediente a ser criado
     * @return resposta com o ingrediente criado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<IngredientResponse> create(@Valid @RequestBody CreateIngredientRequest request) {
        System.out.println(request.getPrice());
        Ingredient ingredient = createIngredientUseCase.execute(
                request.getName(),
                request.getType(),
                request.getQuantity(),
                request.getUnit(),
                request.getPrice()
        );


        return new ApiResponse<>(
                "Ingredient created successfully",
                IngredientApiMapper.toResponse(ingredient)
        );
    }

    /**
     * Lista todos os ingredientes cadastrados.
     *
     * @return lista de ingredientes
     */
    @GetMapping
    public List<IngredientResponse> list() {
        return listIngredientsUseCase.execute()
                .stream()
                .map(IngredientApiMapper::toResponse)
                .toList();
    }

    /**
     * Retorna o volume total de ingredientes agrupado por tipo.
     *
     * @return lista com o volume total de cada tipo de ingrediente
     */
    @GetMapping("/volume")
    public List<IngredientVolumeByTypeResponse> getTotalVolumeByType() {
        return getTotalVolumeByIngredientTypeUseCase.execute()
                .stream()
                .map(IngredientApiMapper::toResponse)
                .toList();
    }
}
