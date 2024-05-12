package com.mballem.demoparkapi.web.exception;

import com.mballem.demoparkapi.exception.EntityNotFoundException;
import com.mballem.demoparkapi.exception.PasswordInvalidException;
import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    // Método para lidar com exceções de senha inválida
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    // Método para lidar com exceções de entidade não encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // Método para lidar com exceções de violação de unicidade de nome de usuário
    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    // Método para lidar com exceções de argumento inválido no método
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", result));
    }
}

/*
@Slf4j: Esta é uma anotação do projeto Lombok que gera automaticamente um logger estático na classe.
É usada para logar informações de erro.

@RestControllerAdvice: Esta é uma anotação do Spring que marca a classe como um componente global que
trata exceções lançadas por métodos anotados com @RequestMapping e seus derivados.

Métodos @ExceptionHandler: Cada um desses métodos trata uma exceção específica lançada durante a execução
da API.

passwordInvalidException: Trata exceções de senha inválida. Retorna uma resposta com status 400
(Bad Request).
entityNotFoundException: Trata exceções de entidade não encontrada. Retorna uma resposta com status 404
(Not Found).
uniqueViolationException: Trata exceções de violação de unicidade de nome de usuário. Retorna uma
resposta com status 409 (Conflict).
methodArgumentNotValidException: Trata exceções de argumento inválido no método. Retorna uma
resposta com status 422 (Unprocessable Entity).
ResponseEntity<ErrorMessage>: Retorna uma resposta HTTP com um corpo que contém detalhes sobre o erro
ocorrido, incluindo o código de status, mensagem de erro e outros detalhes relevantes.

Essa classe ApiExceptionHandler é responsável por lidar com exceções lançadas pela API durante a
execução e retornar respostas apropriadas com detalhes sobre os erros ocorridos. Ela ajuda a manter uma
resposta consistente e adequada para diferentes tipos de erros que podem ocorrer na aplicação.
*/
