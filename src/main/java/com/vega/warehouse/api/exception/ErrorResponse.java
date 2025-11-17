package com.vega.warehouse.api.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe que representa uma resposta de erro padronizada da API.
 * <p>
 * Utilizada pelo GlobalExceptionHandler para formatar
 * respostas de erro HTTP de forma consistente.
 * </p>
 *
 * @author LemosFTW
 */
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldError> fieldErrors;

    /**
     * Construtor para resposta de erro sem detalhes de campos.
     *
     * @param timestamp data e hora do erro
     * @param status código HTTP de status
     * @param error tipo do erro
     * @param message mensagem de erro
     * @param path caminho da requisição que causou o erro
     */
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    /**
     * Construtor para resposta de erro com detalhes de campos.
     *
     * @param timestamp data e hora do erro
     * @param status código HTTP de status
     * @param error tipo do erro
     * @param message mensagem de erro
     * @param path caminho da requisição que causou o erro
     * @param fieldErrors lista de erros de validação por campo
     */
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, List<FieldError> fieldErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * Classe interna que representa um erro de validação em um campo específico.
     *
     * @author LemosFTW
     */
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}

