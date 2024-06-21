package com.mballem.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "clientes")
@EntityListeners(AuditingEntityListener.class)
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do cliente
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    // CPF do cliente (único)
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    // Relacionamento um-para-um com a entidade Usuario
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

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

    // Método equals para comparar objetos Cliente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    // Método hashCode para calcular o hash do objeto Cliente
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
