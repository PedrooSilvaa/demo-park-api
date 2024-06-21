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

    @NotBlank(message = "{NotBlank.usuarioCreateDto.username}")
    @Email(message = "{Email.usuarioCreateDto.username}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;
    @NotBlank(message = "{NotBlank.usuarioCreateDto.password}")
    @Size(min = 6, max = 6, message = "{Size.usuarioCreateDto.password}")
    private String password;
}
