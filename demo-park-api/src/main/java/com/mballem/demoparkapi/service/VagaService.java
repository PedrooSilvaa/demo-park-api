package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Vaga;
import com.mballem.demoparkapi.exception.CodigoUniqueViolationException;
import com.mballem.demoparkapi.exception.EntityNotFoundException;
import com.mballem.demoparkapi.exception.VagaDisponivelException;
import com.mballem.demoparkapi.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mballem.demoparkapi.entity.Vaga.StatusVaga.LIVRE;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    /**
     * Salva uma vaga no banco de dados.
     *
     * @param vaga Vaga a ser salva
     * @return Vaga salva
     * @throws CodigoUniqueViolationException Exceção lançada se o código da vaga já existir no sistema
     */
    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagaRepository.save(vaga); // Salva a vaga no banco de dados
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException("Vaga", vaga.getCodigo()); // Lança exceção se o código da vaga já existir
        }
    }

    /**
     * Busca uma vaga pelo código.
     *
     * @param codigo Código da vaga a ser buscada
     * @return Vaga encontrada
     * @throws EntityNotFoundException Exceção lançada se a vaga não for encontrada
     */
    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException("Vaga", codigo)
        );
    }

    /**
     * Busca a primeira vaga livre.
     *
     * @return Vaga livre encontrada
     * @throws VagaDisponivelException Exceção lançada se nenhuma vaga livre for encontrada
     */
    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new VagaDisponivelException()
        );
    }
}
