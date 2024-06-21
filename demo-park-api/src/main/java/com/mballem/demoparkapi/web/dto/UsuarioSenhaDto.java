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
    @NotBlank(message = "{NotBlank.usuarioSenhaDto.senhaAtual}")
    @Size(min = 6, max = 6, message = "{Size.usuarioSenhaDto.senhaAtual}")
    private String senhaAtual;
    @NotBlank(message = "{NotBlank.usuarioSenhaDto.novaSenha}")
    @Size(min = 6, max = 6, message = "{Size.usuarioSenhaDto.novaSenha}")
    private String novaSenha;
    @NotBlank(message = "{NotBlank.usuarioSenhaDto.confirmaSenha}")
    @Size(min = 6, max = 6, message = "{Size.usuarioSenhaDto.confirmaSenha}")
    private String confirmaSenha;
}
