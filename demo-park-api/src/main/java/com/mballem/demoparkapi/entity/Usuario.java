package com.mballem.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    @Column(name = "criado_por")
    private String criadoPor;
    @Column(name = "modificado_por")
    private String modificadoPor;

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }
}

/*
Anotações Lombok:

@Getter, @Setter, @NoArgsConstructor: Essas anotações do Lombok ajudam a gerar automaticamente getters,
setters e um construtor sem argumentos para a classe Usuario.
Anotações JPA:

@Entity: Indica que esta classe é uma entidade JPA.
@Table(name = "usuarios"): Especifica o nome da tabela no banco de dados.
@Id, @GeneratedValue, @Column: Essas anotações são usadas para mapear os campos da classe para colunas da
tabela no banco de dados.
Atributos da Entidade:

id, username, password, role: São os campos da entidade Usuario.
dataCriacao, dataModificacao, criadoPor, modificadoPor: São campos adicionais para rastrear informações
de criação e modificação de registros.
Enumeração Role:

Define uma enumeração para representar os papéis do usuário.
Métodos Override:

equals(), hashCode(), toString(): Esses métodos são sobrescritos para fornecer uma implementação
personalizada de comparação, cálculo de hash e representação de string para a classe Usuario.
Essa classe representa a estrutura da entidade de usuário em um sistema, com os atributos necessários e
métodos auxiliares para manipulação de objetos Usuario.

*/