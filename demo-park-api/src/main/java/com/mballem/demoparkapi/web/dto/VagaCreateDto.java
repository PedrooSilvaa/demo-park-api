package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VagaCreateDto {
    @NotBlank(message = "{NotBlank.vagaCreateDto.codigo}")
    @Size(min = 4, max = 4)
    private String codigo;
    @NotBlank(message = "{NotBlank.vagaCreateDto.status}")
    @Pattern(regexp = "LIVRE|OCUPADA", message = "{Pattern.vagaCreateDto.status}")
    private String status;
}
