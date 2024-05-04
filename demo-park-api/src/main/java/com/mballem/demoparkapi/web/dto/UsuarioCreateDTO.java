package com.mballem.demoparkapi.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDTO {

    private String username;
    private String password;

}
