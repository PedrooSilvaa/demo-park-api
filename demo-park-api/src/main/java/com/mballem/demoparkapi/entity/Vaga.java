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
@Table(name = "vagas")
@EntityListeners(AuditingEntityListener.class)
public class Vaga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código da vaga (único)
    @Column(name = "codigo", nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVaga status;

    // Descrição da vaga
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

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

    public enum StatusVaga {
        LIVRE, OCUPADA
    }

    // Método equals para comparar objetos Vaga
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vaga vaga = (Vaga) o;
        return Objects.equals(id, vaga.id);
    }

    // Método hashCode para calcular o hash do objeto Vaga
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
