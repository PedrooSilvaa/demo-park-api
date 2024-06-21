package com.mballem.demoparkapi.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    /**
     * Construtor padrão vazio.
     */
    public ErrorMessage() {
    }

    /**
     * Construtor para inicializar ErrorMessage com informações básicas e uma mensagem simples.
     * @param request HttpServletRequest para obter informações de URI e método.
     * @param status HttpStatus para definir o código de status HTTP da resposta.
     * @param message Mensagem de erro a ser retornada.
     */
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    /**
     * Construtor para inicializar ErrorMessage com informações detalhadas, incluindo erros de validação.
     * @param request HttpServletRequest para obter informações de URI e método.
     * @param status HttpStatus para definir o código de status HTTP da resposta.
     * @param message Mensagem de erro a ser retornada.
     * @param result BindingResult contendo os erros de validação.
     * @param messageSource MessageSource para obter mensagens localizadas a partir dos códigos de erro.
     */
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result, MessageSource messageSource) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result, messageSource, request.getLocale());
    }

    /**
     * Método privado para adicionar erros de validação ao objeto ErrorMessage.
     * @param result BindingResult contendo os erros de validação.
     * @param messageSource MessageSource para obter mensagens localizadas a partir dos códigos de erro.
     * @param locale Locale para determinar o idioma da mensagem de erro.
     */
    private void addErrors(BindingResult result, MessageSource messageSource, Locale locale) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            String code = fieldError.getCodes()[0]; // Obtém o código da mensagem de erro
            String message = messageSource.getMessage(code, fieldError.getArguments(), locale); // Obtém a mensagem localizada
            this.errors.put(fieldError.getField(), message); // Adiciona o erro ao mapa de erros
        }
    }
}
