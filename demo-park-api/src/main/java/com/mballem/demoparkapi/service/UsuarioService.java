package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//Transactional -> precisa ser da spring
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        //findById por padrão retorna um metodo optional
        //nesse caso sera usado o orElseThrow() q sera lancado ou o usuario ou uma exceção
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado")
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String password) {
        Usuario user = buscarPorId(id);
        /*não foi preciso usar um metodo update pois quando buscamos pelo usuario
        * o hibernate só vai finalizar no final da requisicao e resposta
        * e assim que é usado o setPassword ele entende q é preciso mudar a senha
        * pooque o hibernate usa uma memoria de cache*/
        user.setPassword(password);
        return user;
    }
}
