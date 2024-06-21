package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EstacionamentoCreateDto {
    @NotBlank(message = "{NotBlank.estacionamentoCreateDto.placa}")
    @Size(min = 8, max = 8, message = "{Size.estacionamentoCreateDto.placa}")
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "{Pattern.estacionamentoCreateDto.placa}")
    private String placa;
    @NotBlank(message = "{NotBlank.estacionamentoCreateDto.marca}")
    private String marca;
    @NotBlank(message = "{NotBlank.estacionamentoCreateDto.modelo}")
    private String modelo;
    @NotBlank(message = "{NotBlank.estacionamentoCreateDto.cor}")
    private String cor;
    @NotBlank(message = "{NotBlank.estacionamentoCreateDto.clienteCpf}")
    @Size(min = 11, max = 11, message = "{Size.estacionamentoCreateDto.clienteCpf}")
    @CPF(message = "{CPF.estacionamentoCreateDto.clienteCpf}")
    private String clienteCpf;
}
