package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.exception.EntityNotFoundException;
import com.mballem.demoparkapi.exception.NewPasswordInvalidException;
import com.mballem.demoparkapi.exception.PasswordInvalidException;
import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;
import com.mballem.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Salva um usuário no banco de dados.
     *
     * @param usuario Usuário a ser salvo
     * @return Usuário salvo
     * @throws UsernameUniqueViolationException Exceção lançada se o nome de usuário já existir no sistema
     */
    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario); // Salva o usuário no banco de dados
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(usuario.getUsername()); // Lança exceção se o nome de usuário já existir
        }
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id ID do usuário a ser buscado
     * @return Usuário encontrado
     * @throws EntityNotFoundException Exceção lançada se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuário", String.valueOf(id))
        );
    }

    /**
     * Edita a senha de um usuário.
     *
     * @param id           ID do usuário
     * @param senhaAtual   Senha atual do usuário
     * @param novaSenha    Nova senha desejada
     * @param confirmaSenha Confirmação da nova senha
     * @return Usuário com a senha atualizada
     * @throws NewPasswordInvalidException Exceção lançada se a nova senha não for válida
     * @throws PasswordInvalidException    Exceção lançada se a senha atual não for válida
     */
    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new NewPasswordInvalidException(); // Lança exceção se a nova senha não for válida
        }

        Usuario user = buscarPorId(id); // Busca o usuário pelo ID
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new PasswordInvalidException(); // Lança exceção se a senha atual não for válida
        }

        user.setPassword(passwordEncoder.encode(novaSenha)); // Define a nova senha criptografada
        return user; // Retorna o usuário com a senha atualizada
    }

    /**
     * Busca todos os usuários cadastrados.
     *
     * @return Lista de usuários encontrados
     */
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll(); // Busca todos os usuários cadastrados
    }

    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username Nome de usuário a ser buscado
     * @return Usuário encontrado
     * @throws EntityNotFoundException Exceção lançada se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Usuario", username)
        );
    }

    /**
     * Busca a role de um usuário pelo nome de usuário.
     *
     * @param username Nome de usuário
     * @return Role do usuário
     */
    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username); // Busca a role do usuário pelo nome de usuário
    }
}
