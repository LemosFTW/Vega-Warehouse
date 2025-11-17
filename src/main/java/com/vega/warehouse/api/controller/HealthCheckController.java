package com.vega.warehouse.api.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para verificação de saúde da aplicação.
 * <p>
 * Endpoint simples para verificar se a API está em execução.
 * </p>
 *
 * @author LemosFTW
 */
@RestController
public class HealthCheckController {

    /**
     * Verifica se a API está em execução.
     *
     * @return mensagem indicando que a API está rodando
     */
    @GetMapping("/healthcheck")
    public String ping() {
        return "Api is running";
    }
}
