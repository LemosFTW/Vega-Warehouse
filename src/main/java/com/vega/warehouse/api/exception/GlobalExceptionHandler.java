package com.vega.warehouse.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler global de exceções da aplicação.
 * <p>
 * Centraliza o tratamento de exceções e converte para respostas HTTP
 * padronizadas com mensagens de erro apropriadas.
 * </p>
 *
 * @author LemosFTW
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções de violação de constraints de validação.
     *
     * @param ex exceção de violação de constraint
     * @param request requisição HTTP
     * @return resposta de erro com detalhes das violações
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldError> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String field = getFieldName(violation);
                    String message = violation.getMessage();
                    return new ErrorResponse.FieldError(field, message);
                })
                .collect(Collectors.toList());

        String errorMessage = fieldErrors.stream()
                .map(ErrorResponse.FieldError::getMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                errorMessage,
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de parâmetros obrigatórios ausentes na requisição.
     *
     * @param ex exceção de parâmetro ausente
     * @param request requisição HTTP
     * @return resposta de erro indicando o parâmetro ausente
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        String message = String.format("O parâmetro '%s' é obrigatório", ex.getParameterName());

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Parâmetro Obrigatório Ausente",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de tipo de parâmetro inválido.
     *
     * @param ex exceção de tipo inválido
     * @param request requisição HTTP
     * @return resposta de erro indicando o tipo esperado
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String parameterName = ex.getName();
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";
        String providedValue = ex.getValue() != null ? ex.getValue().toString() : "null";

        String message;
        if (parameterName.equals("tipo")) {
            message = String.format("O valor '%s' não é um tipo de ingrediente válido. Valores aceitos: SECO, LIQUIDO, REFRIGERADO", providedValue);
        } else {
            message = String.format("O parâmetro '%s' deve ser do tipo %s, mas foi recebido: %s", parameterName, requiredType, providedValue);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Tipo de Parâmetro Inválido",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de argumentos de método inválidos (validação de bean).
     *
     * @param ex exceção de argumento inválido
     * @param request requisição HTTP
     * @return resposta de erro com detalhes dos campos inválidos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.add(new ErrorResponse.FieldError(fieldName, errorMessage));
        });

        String errorMessage = fieldErrors.stream()
                .map(ErrorResponse.FieldError::getMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                errorMessage,
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de corpo de requisição malformado ou inválido.
     *
     * @param ex exceção de mensagem não legível
     * @param request requisição HTTP
     * @return resposta de erro indicando problema no corpo da requisição
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição Inválida",
                "O corpo da requisição está malformado ou contém dados inválidos",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de argumento ilegal (regras de negócio).
     *
     * @param ex exceção de argumento ilegal
     * @param request requisição HTTP
     * @return resposta de erro com a mensagem da exceção
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação de Negócio",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de rota não encontrada (404).
     *
     * @param ex exceção de handler não encontrado
     * @param request requisição HTTP
     * @return resposta de erro 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso Não Encontrado",
                String.format("O endpoint '%s %s' não foi encontrado", ex.getHttpMethod(), ex.getRequestURL()),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Trata exceções genéricas não tratadas anteriormente.
     *
     * @param ex exceção genérica
     * @param request requisição HTTP
     * @return resposta de erro interno do servidor
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Extrai o nome do campo de uma violação de constraint.
     *
     * @param violation violação de constraint
     * @return nome do campo
     */
    private String getFieldName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        if (propertyPath.contains(".")) {
            return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
        }
        return propertyPath;
    }
}

