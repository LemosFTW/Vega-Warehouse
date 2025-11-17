package com.vega.warehouse.api.dto.response;

/**
 * Classe genérica para respostas padronizadas da API.
 * <p>
 * Encapsula uma mensagem e dados em uma estrutura consistente
 * para todas as respostas de sucesso da API.
 * </p>
 *
 * @param <T> tipo dos dados da resposta
 * @author LemosFTW
 */
public class ApiResponse<T> {
    private String message;
    private T data;

    /**
     * Construtor da resposta da API.
     *
     * @param message mensagem de sucesso
     * @param data dados da resposta
     */
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
