package com.mballem.demoparkapi.jwt;

import com.mballem.demoparkapi.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Representa os detalhes do usuário para autenticação JWT.
 * Estende a classe User do Spring Security para fornecer informações adicionais.
 */
public class JwtUserDetails extends User {

    private Usuario usuario;

    /**
     * Construtor que inicializa os detalhes do usuário com base nos dados do objeto Usuario.
     *
     * @param usuario Objeto Usuario contendo as informações do usuário
     */
    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }

    /**
     * Obtém o ID do usuário.
     *
     * @return ID do usuário
     */
    public Long getId() {
        return this.usuario.getId();
    }

    /**
     * Obtém o papel (role) do usuário.
     *
     * @return Papel do usuário
     */
    public String getRole() {
        return this.usuario.getRole().name();
    }
}
