package com.mballem.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Nome de usuário (único)
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    // Senha do usuário
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    // Papel (ROLE_ADMIN ou ROLE_CLIENTE)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;

    // Data de criação do registro (audit)
    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    // Data da última modificação do registro (audit)
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;

    // Usuário que criou o registro (audit)
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;

    // Usuário que modificou o registro pela última vez (audit)
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    // Enumeração dos papéis de usuário
    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE
    }

    // Método equals para comparar objetos Usuario
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    // Método hashCode para calcular o hash do objeto Usuario
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString para representação em String do objeto Usuario
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }
}
