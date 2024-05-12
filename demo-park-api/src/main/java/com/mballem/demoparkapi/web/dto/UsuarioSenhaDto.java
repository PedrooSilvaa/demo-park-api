package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDto {
    // Anotações de validação para garantir que o campo não esteja em branco e tenha um tamanho específico
    @NotBlank
    @Size(min = 6, max = 6)
    private String senhaAtual;

    // Anotações de validação para garantir que o campo não esteja em branco e tenha um tamanho específico
    @NotBlank
    @Size(min = 6, max = 6)
    private String novaSenha;

    // Anotações de validação para garantir que o campo não esteja em branco e tenha um tamanho específico
    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmaSenha;
}
/*
Anotações de Validação (@NotBlank, @Size): Essas anotações são usadas para impor restrições nos campos
do DTO. @NotBlank garante que o campo não esteja em branco e @Size garante que o campo tenha um tamanho
mínimo e máximo.

@Getter, @Setter: Essas anotações são do projeto Lombok e são usadas para gerar automaticamente getters
e setters para os campos da classe.

@NoArgsConstructor, @AllArgsConstructor: Essas anotações do Lombok são usadas para gerar automaticamente
construtores sem argumentos e construtores com todos os argumentos, respectivamente.

@ToString: Esta anotação do Lombok gera automaticamente o método toString() para a classe, que retorna
uma representação de string dos seus campos.

Esta classe UsuarioSenhaDto é um DTO (Data Transfer Object) que é usado para transferir dados entre a
camada de controle e a camada de serviço da aplicação. Ele representa os dados necessários para atualizar
a senha de um usuário, incluindo a senha atual, a nova senha e a confirmação da nova senha. As anotações
de validação garantem que os dados fornecidos atendam aos critérios especificados antes de serem
processados pela aplicação.
*/