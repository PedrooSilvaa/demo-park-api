package com.mballem.demoparkapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 2;
    public static final long EXPIRE_MINUTES = 30;

    private JwtUtils() {
    }

    /**
     * Gera a chave secreta usada para assinar e verificar o token JWT.
     *
     * @return SecretKey gerada
     */
    private static SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Calcula a data de expiração com base na data de emissão do token.
     *
     * @param start Data de emissão do token
     * @return Data de expiração do token
     */
    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Cria um token JWT com o username e o papel (role) do usuário.
     *
     * @param username Username do usuário
     * @param role     Papel (role) do usuário
     * @return JwtToken contendo o token JWT criado
     */
    public static JwtToken createToken(String username, String role) {
        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);

        String token = Jwts.builder()
                .header().add("typ", "JWT")
                .and()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(limit)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .claim("role", role)
                .compact();

        return new JwtToken(token);
    }

    /**
     * Extrai e retorna as claims (informações) do token JWT.
     *
     * @param token Token JWT
     * @return Claims extraídas do token JWT
     */
    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build().parseSignedClaims(refactorToken(token)).getPayload();
        } catch (JwtException ex) {
            log.error(String.format("Token inválido %s", ex.getMessage()));
        }
        return null;
    }

    /**
     * Extrai e retorna o username do token JWT.
     *
     * @param token Token JWT
     * @return Username extraído do token JWT
     */
    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Verifica se o token JWT é válido.
     *
     * @param token Token JWT
     * @return true se o token JWT for válido, false caso contrário
     */
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build().parseSignedClaims(refactorToken(token));
            return true;
        } catch (JwtException ex) {
            log.error(String.format("Token inválido %s", ex.getMessage()));
        }
        return false;
    }

    /**
     * Refatora o token removendo o prefixo "Bearer " se presente.
     *
     * @param token Token JWT
     * @return Token JWT refatorado
     */
    private static String refactorToken(String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}
