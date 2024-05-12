package com.mballem.demoparkapi.web.dto.mapper;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.web.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    // Método para converter um objeto UsuarioCreateDto em um objeto Usuario
    public static Usuario toUsuario(UsuarioCreateDto createDto) {
        return new ModelMapper().map(createDto, Usuario.class);
    }

    // Método para converter um objeto Usuario em um objeto UsuarioResponseDto
    public static UsuarioResponseDto toDto(Usuario usuario) {
        // Extrai o papel do usuário da enumeração e mapeia para uma string
        String role = usuario.getRole().name().substring("ROLE_".length());
        // Define um mapeamento personalizado para mapear a propriedade 'role' de Usuario para UsuarioResponseDto
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        // Cria um objeto ModelMapper e adiciona o mapeamento personalizado
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        // Converte o objeto Usuario para UsuarioResponseDto usando o mapeamento personalizado
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    // Método para converter uma lista de Usuario em uma lista de UsuarioResponseDto
    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
        // Usa streams para mapear cada usuário para um UsuarioResponseDto e coletar em uma lista
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}

/*
toUsuario(UsuarioCreateDto createDto): Este método recebe um objeto UsuarioCreateDto e usa um
ModelMapper para mapear seus atributos para um objeto Usuario.

toDto(Usuario usuario): Este método recebe um objeto Usuario e usa um ModelMapper para
mapear seus atributos para um objeto UsuarioResponseDto. Ele também extrai o papel (role)
do usuário e o define na resposta.

toListDto(List<Usuario> usuarios): Este método recebe uma lista de objetos Usuario,
usa streams para mapear cada objeto para um UsuarioResponseDto usando o método toDto, e
então coleta os resultados em uma lista.

O ModelMapper é uma biblioteca usada para mapear objetos de um tipo para outro de forma
automática e configurável, baseando-se nas correspondências entre os nomes das
propriedades. Ele é utilizado aqui para facilitar a conversão entre os objetos DTO e as entidades
de domínio.
*/