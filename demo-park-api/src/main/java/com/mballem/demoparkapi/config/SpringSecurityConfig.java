package com.mballem.demoparkapi.config;

import com.mballem.demoparkapi.jwt.JwtAuthenticationEntryPoint;
import com.mballem.demoparkapi.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableMethodSecurity // Habilita a segurança baseada em métodos
@EnableWebMvc // Habilita o suporte ao Spring MVC
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                // Desabilita CSRF, pois usamos JWT que não é estadoful
                .csrf(csrf -> csrf.disable())
                // Desabilita o formulário de login padrão do Spring Security
                .formLogin(form -> form.disable())
                // Desabilita a autenticação básica HTTP
                .httpBasic(basic -> basic.disable())
                // Configuração de autorização para diferentes tipos de requisição
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público às requisições POST para "/api/v1/usuarios" e "/api/v1/auth"
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/usuarios")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/auth")).permitAll()
                        // Permite acesso público aos endpoints do Swagger e da documentação
                        .requestMatchers(
                                antMatcher("/docs-park.html"),
                                antMatcher("/docs-park/**"),
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/webjars/**")
                        ).permitAll()
                        // Todas as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                )
                // Define que a aplicação é stateless, ou seja, não mantém estado de sessão
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Adiciona o filtro de autorização JWT antes do filtro padrão de UsernamePasswordAuthenticationFilter
                .addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                )
                // Configura o tratamento de exceção para entrada não autorizada
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                .build();
    }

    @Bean
    public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
