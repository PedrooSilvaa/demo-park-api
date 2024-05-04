package com.mballem.demoparkapi.web.dto.mapper;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.web.dto.UsuarioCreateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDTO createDTO){
        return new ModelMapper().map(createDTO, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario){
        // nessa parte pegamos oq esta escrito no metodo role e tirando o role apartir do substring
        // deixando somente cliente ou admin
        String role = usuario.getRole().name().substring("ROLE_".length());
        /*
        * Agora vamos pegar o valor de role e inserir no responseDto
        */
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        /**/
        ModelMapper mapper =  new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toUnmodifiableList());
    }

}
