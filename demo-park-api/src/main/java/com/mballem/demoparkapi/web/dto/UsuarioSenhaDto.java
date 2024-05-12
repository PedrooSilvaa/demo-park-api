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
    @NotBlank
    @Size(min = 6, max = 6)
    private String senhaAtual;
    @NotBlank
    @Size(min = 6, max = 6)
    private String novaSenha;
    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmaSenha;
}
