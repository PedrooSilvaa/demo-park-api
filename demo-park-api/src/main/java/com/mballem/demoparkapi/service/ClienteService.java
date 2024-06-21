package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Cliente;
import com.mballem.demoparkapi.exception.CpfUniqueViolationException;
import com.mballem.demoparkapi.exception.EntityNotFoundException;
import com.mballem.demoparkapi.repository.ClienteRepository;
import com.mballem.demoparkapi.repository.projection.ClienteProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Salva um cliente no banco de dados.
     *
     * @param cliente Cliente a ser salvo
     * @return Cliente salvo
     * @throws CpfUniqueViolationException Exceção lançada se o CPF já existir no sistema
     */
    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente); // Salva o cliente utilizando o repositório
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(cliente.getCpf()); // Lança exceção se o CPF já existir
        }
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id ID do cliente a ser buscado
     * @return Cliente encontrado
     * @throws EntityNotFoundException Exceção lançada se o cliente não for encontrado
     */
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cliente", String.valueOf(id))
        );
    }

    /**
     * Busca todos os clientes paginados.
     *
     * @param pageable Objeto Pageable para paginação
     * @return Página de clientes encontrados
     */
    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable); // Busca todos os clientes utilizando paginação
    }

    /**
     * Busca um cliente pelo ID do usuário associado.
     *
     * @param id ID do usuário associado ao cliente
     * @return Cliente encontrado
     */
    @Transactional(readOnly = true)
    public Cliente buscarPorUsuarioId(Long id) {
        return clienteRepository.findByUsuarioId(id); // Busca cliente pelo ID do usuário associado
    }

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf CPF do cliente a ser buscado
     * @return Cliente encontrado
     * @throws EntityNotFoundException Exceção lançada se o cliente não for encontrado
     */
    @Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException("Cliente", cpf)
        );
    }
}
