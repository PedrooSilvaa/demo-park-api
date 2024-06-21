package com.mballem.demoparkapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing // Habilita a auditoria do JPA
@Configuration
public class SpringJpaAuditingConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Obtém o contexto de segurança do Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se há autenticação e se o usuário está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            // Retorna o nome do usuário autenticado como o auditor atual
            return Optional.of(authentication.getName());
        }

        // Retorna um Optional vazio se não houver autenticação válida
        return Optional.empty();
    }
}
