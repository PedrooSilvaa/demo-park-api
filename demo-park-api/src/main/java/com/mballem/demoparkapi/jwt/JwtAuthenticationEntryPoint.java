package com.mballem.demoparkapi.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Método chamado quando ocorre uma falha de autenticação durante uma requisição.
     * Registra uma mensagem informativa no log com o status HTTP 401 e a mensagem da exceção de autenticação.
     * Define o cabeçalho www-authenticate na resposta HTTP para especificar o esquema de autenticação Bearer e o realm.
     * Envia uma resposta de erro HTTP 401 para indicar que a autenticação é necessária para acessar o recurso solicitado.
     *
     * @param request       HttpServletRequest que originou a exceção
     * @param response      HttpServletResponse para enviar a resposta de erro
     * @param authException AuthenticationException que foi lançada durante a tentativa de autenticação
     * @throws IOException      se ocorrer um erro de entrada ou saída durante o processamento da requisição
     * @throws ServletException se ocorrer um erro de servlet durante o processamento da requisição
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Http Status 401 {}", authException.getMessage());
        response.setHeader("www-authenticate", "Bearer realm='/api/v1/auth'");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
