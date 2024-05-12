package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.exception.EntityNotFoundException;
import com.mballem.demoparkapi.exception.PasswordInvalidException;
import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;
import com.mballem.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    // Injeção do repositório de usuários
    private final UsuarioRepository usuarioRepository;

    // Método para salvar um novo usuário
    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            // Salva o usuário no banco de dados
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            // Se ocorrer uma exceção de violação de integridade de dados, lança uma exceção personalizada
            throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername()));
        }
    }

    // Método para buscar um usuário pelo ID
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        // Busca o usuário no banco de dados pelo ID
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    // Método para editar a senha de um usuário
    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        // Verifica se a nova senha é igual à confirmação de senha
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        // Busca o usuário pelo ID
        Usuario user = buscarPorId(id);
        // Verifica se a senha atual fornecida corresponde à senha do usuário
        if (!user.getPassword().equals(senhaAtual)) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        // Atualiza a senha do usuário e retorna o usuário modificado
        user.setPassword(novaSenha);
        return user;
    }

    // Método para buscar todos os usuários
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        // Retorna todos os usuários do banco de dados
        return usuarioRepository.findAll();
    }
}
