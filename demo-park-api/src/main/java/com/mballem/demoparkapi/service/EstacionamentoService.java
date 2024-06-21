package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Cliente;
import com.mballem.demoparkapi.entity.ClienteVaga;
import com.mballem.demoparkapi.entity.Vaga;
import com.mballem.demoparkapi.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;

    /**
     * Realiza o check-in de um cliente em uma vaga de estacionamento.
     *
     * @param clienteVaga Registro de cliente em vaga para check-in
     * @return Registro salvo após o check-in
     */
    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga) {
        Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
        clienteVaga.setCliente(cliente); // Define o cliente no registro de cliente em vaga

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga); // Define a vaga no registro de cliente em vaga

        clienteVaga.setDataEntrada(LocalDateTime.now()); // Define a data de entrada

        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo()); // Gera um recibo único

        return clienteVagaService.salvar(clienteVaga); // Salva o registro de cliente em vaga
    }

    /**
     * Realiza o check-out de um cliente em uma vaga de estacionamento.
     *
     * @param recibo Recibo de check-in do cliente
     * @return Registro atualizado após o check-out
     */
    @Transactional
    public ClienteVaga checkOut(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo); // Busca o registro pelo recibo

        LocalDateTime dataSaida = LocalDateTime.now(); // Obtém a data de saída atual

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida); // Calcula o custo do estacionamento
        clienteVaga.setValor(valor); // Define o valor calculado no registro

        long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf()); // Obtém o total de vezes que o cliente completou o estacionamento

        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes); // Calcula o desconto aplicável
        clienteVaga.setDesconto(desconto); // Define o desconto no registro

        clienteVaga.setDataSaida(dataSaida); // Define a data de saída no registro
        clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE); // Libera a vaga utilizada

        return clienteVagaService.salvar(clienteVaga); // Salva o registro atualizado após o check-out
    }
}
