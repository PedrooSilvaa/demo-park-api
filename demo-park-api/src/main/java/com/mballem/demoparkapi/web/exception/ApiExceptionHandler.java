package com.mballem.demoparkapi.web.exception;

import com.mballem.demoparkapi.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe para lidar com exceções customizadas e retornar respostas adequadas ao cliente.
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    /**
     * Trata exceções de credenciais inválidas.
     */
    @ExceptionHandler(InvalidCredencialException.class)
    public ResponseEntity<ErrorMessage> invalidCredencialException(InvalidCredencialException ex, HttpServletRequest request) {
        Object[] params = new Object[]{ex.getUsername()};
        String message = messageSource.getMessage("exception.invalidCredencialException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, message));
    }

    /**
     * Trata exceções de senha inválida.
     */
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(HttpServletRequest request) {
        String message = messageSource.getMessage("exception.passwordInvalidException", null, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, message));
    }

    /**
     * Trata exceções de nova senha inválida.
     */
    @ExceptionHandler(NewPasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> newPasswordInvalidException(HttpServletRequest request) {
        String message = messageSource.getMessage("exception.newPasswordInvalidException", null, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, message));
    }

    /**
     * Trata exceções de violação de unicidade de username.
     */
    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> usernameUniqueViolationException(UsernameUniqueViolationException ex, HttpServletRequest request) {
        Object[] params = new Object[]{ex.getUsername()};
        String message = messageSource.getMessage("exception.usernameUniqueViolationException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    /**
     * Trata exceções de violação de unicidade de CPF.
     */
    @ExceptionHandler(CpfUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> cpfUniqueViolationException(CpfUniqueViolationException ex, HttpServletRequest request) {
        Object[] params = new Object[]{ex.getCpf()};
        String message = messageSource.getMessage("exception.cpfUniqueViolationException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    /**
     * Trata exceções de recibo de check-in não encontrado.
     */
    @ExceptionHandler(ReciboCheckInNotFoundException.class)
    public ResponseEntity<ErrorMessage> reciboCheckInNotFoundException(ReciboCheckInNotFoundException ex, HttpServletRequest request) {
        Object[] params = new Object[]{ex.getRecibo()};
        String message = messageSource.getMessage("exception.reciboCheckInNotFoundException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
    }

    /**
     * Trata exceções de entidade não encontrada.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        Object[] params = new Object[]{ex.getRecurso(), ex.getCodigo()};
        String message = messageSource.getMessage("exception.entityNotFoundException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
    }

    /**
     * Trata exceções de violação de unicidade de código.
     */
    @ExceptionHandler(CodigoUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> codigoUniqueViolationException(CodigoUniqueViolationException ex, HttpServletRequest request) {
        Object[] params = new Object[]{ex.getRecurso(), ex.getCodigo()};
        String message = messageSource.getMessage("exception.codigoUniqueViolationException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    /**
     * Trata exceções de acesso negado.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    /**
     * Trata exceções de vaga disponível não encontrada.
     */
    @ExceptionHandler(VagaDisponivelException.class)
    public ResponseEntity<ErrorMessage> vagaDisponivelException(RuntimeException ex, HttpServletRequest request) {
        String message = messageSource.getMessage("exception.vagaDisponivelException", null, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
    }

    /**
     * Trata exceções de validação de argumentos do método.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        messageSource.getMessage("message.invalid.field", null, request.getLocale()),
                        result,
                        messageSource)
                );
    }

    /**
     * Trata exceções genéricas não especificadas.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalServerErrorException(Exception ex, HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(
                request, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        log.error("Internal Server Error {} {} ", error, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
