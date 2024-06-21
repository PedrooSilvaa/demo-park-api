package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteCreateDto {
    @NotBlank(message = "{NotBlank.clienteCreateDto.nome}")
    @Size(message = "{Size.clienteCreateDto.nome}")
    private String nome;
    @Size(min = 11, max = 11, message = "{Size.clienteCreateDto.cpf}")
    @CPF(message = "{CPF.clienteCreateDto.cpf}")
    private String cpf;
}
