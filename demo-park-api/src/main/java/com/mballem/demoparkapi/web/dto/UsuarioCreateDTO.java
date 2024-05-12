package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDto {

    // Anotações de validação para garantir que o campo não esteja em branco e siga o formato de e-mail
    @NotBlank
    @Email(message = "formato do e-mail está invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    // Anotações de validação para garantir que o campo não esteja em branco e tenha um tamanho específico
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
/*
Anotações de Validação (@NotBlank, @Email, @Size): Essas anotações são usadas para impor restrições
nos campos do DTO. @NotBlank garante que o campo não esteja em branco, @Email garante que o campo
siga o formato de e-mail especificado e @Size garante que o campo tenha um tamanho mínimo e máximo.

@Getter, @Setter: Essas anotações são do projeto Lombok e são usadas para gerar automaticamente
getters e setters para os campos da classe.

@NoArgsConstructor, @AllArgsConstructor: Essas anotações do Lombok são usadas para gerar
automaticamente construtores sem argumentos e construtores com todos os argumentos, respectivamente.

@ToString: Esta anotação do Lombok gera automaticamente o método toString() para a classe, que
retorna uma representação de string dos seus campos.

Essa classe UsuarioCreateDto é um DTO (Data Transfer Object) que é usado para transferir dados entre a
camada de controle e a camada de serviço da aplicação. Ele representa os dados necessários para criar
um novo usuário, incluindo o nome de usuário (username) e a senha (password). As anotações de validação
garantem que os dados fornecidos atendam aos critérios especificados antes de serem processados pela
aplicação.
*/