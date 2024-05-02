package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario buscarPorId(Long id) {
        //findById por padrão retorna um metodo optional
        //nesse caso sera usado o orElseThrow() q sera lancado ou o usuario ou uma exceção
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado")
        );
    }
}
