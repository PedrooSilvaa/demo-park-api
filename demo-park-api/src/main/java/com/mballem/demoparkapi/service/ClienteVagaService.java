package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.ClienteVaga;
import com.mballem.demoparkapi.exception.ReciboCheckInNotFoundException;
import com.mballem.demoparkapi.repository.ClienteVagaRepository;
import com.mballem.demoparkapi.repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

    private final ClienteVagaRepository repository;

    /**
     * Salva um registro de cliente em vaga no banco de dados.
     *
     * @param clienteVaga Registro de cliente em vaga a ser salvo
     * @return Registro salvo
     */
    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga) {
        return repository.save(clienteVaga); // Salva o registro de cliente em vaga
    }

    /**
     * Busca um registro de cliente em vaga pelo recibo de check-in.
     *
     * @param recibo Recibo de check-in a ser buscado
     * @return Registro encontrado
     * @throws ReciboCheckInNotFoundException Exceção lançada se o recibo não for encontrado
     */
    @Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
        return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
                () -> new ReciboCheckInNotFoundException(recibo)
        );
    }

    /**
     * Obtém o total de vezes que um cliente completou o estacionamento.
     *
     * @param cpf CPF do cliente
     * @return Total de vezes que o cliente completou o estacionamento
     */
    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return repository.countByClienteCpfAndDataSaidaIsNotNull(cpf); // Conta registros completos de estacionamento
    }

    /**
     * Busca todos os registros de cliente em vaga paginados por CPF do cliente.
     *
     * @param cpf      CPF do cliente
     * @param pageable Objeto Pageable para paginação
     * @return Página de registros encontrados
     */
    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
        return repository.findAllByClienteCpf(cpf, pageable); // Busca registros por CPF do cliente
    }

    /**
     * Busca todos os registros de cliente em vaga paginados por ID do usuário.
     *
     * @param id       ID do usuário associado ao cliente
     * @param pageable Objeto Pageable para paginação
     * @return Página de registros encontrados
     */
    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
        return repository.findAllByClienteUsuarioId(id, pageable); // Busca registros por ID do usuário
    }
}
