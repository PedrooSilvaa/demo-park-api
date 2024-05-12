package com.mballem.demoparkapi.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {

    // Atributos da mensagem de erro
    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    // Construtor padrão vazio
    public ErrorMessage() {
    }

    // Construtor para criar uma mensagem de erro com status e mensagem personalizados
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    // Construtor para criar uma mensagem de erro com status, mensagem e erros de validação personalizados
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result);
    }

    // Método privado para adicionar erros de validação ao mapa de erros
    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}

/*
Atributos da Mensagem de Erro: Esses atributos representam os detalhes da mensagem de erro,
incluindo o caminho da solicitação (path), o método HTTP (method), o código de status (status), o
texto do status (statusText), a mensagem de erro (message) e um mapa de erros de validação (errors).

@Getter, @ToString: Essas anotações do projeto Lombok geram automaticamente os métodos getter e
toString() para a classe, respectivamente.

@JsonInclude(JsonInclude.Include.NON_NULL): Esta anotação do Jackson indica que o campo errors só
deve ser incluído na serialização JSON se não for nulo.

Construtores:

ErrorMessage(): Construtor padrão vazio.
ErrorMessage(HttpServletRequest request, HttpStatus status, String message): Construtor que cria
uma mensagem de erro com base no caminho da solicitação, método HTTP, código de status e mensagem
fornecidos.
ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result):
Construtor que cria uma mensagem de erro com base no caminho da solicitação, método HTTP, código de
status, mensagem e erros de validação fornecidos.
addErrors(BindingResult result): Método privado que extrai os erros de validação de um objeto
BindingResult e os adiciona ao mapa de erros da mensagem de erro. Cada erro é mapeado pelo nome do campo
e sua mensagem correspondente.

Esta classe ErrorMessage é usada para encapsular informações detalhadas sobre uma mensagem de erro que
ocorre durante a execução da API. Ela fornece uma estrutura consistente para retornar mensagens de erro
 junto com informações relevantes, como o caminho da solicitação, o método HTTP, o código de status e
 mensagens de erro detalhadas, incluindo erros de validação. Isso ajuda na depuração e comunicação de
 problemas para os clientes da API.*/