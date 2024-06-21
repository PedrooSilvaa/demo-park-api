package com.mballem.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "clientes_tem_vagas")
@EntityListeners(AuditingEntityListener.class)
public class ClienteVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Número do recibo da vaga
    @Column(name = "numero_recibo", nullable = false, unique = true, length = 15)
    private String recibo;

    // Placa do veículo na vaga
    @Column(name = "placa", nullable = false, length = 8)
    private String placa;

    // Marca do veículo na vaga
    @Column(name = "marca", nullable = false, length = 45)
    private String marca;

    // Modelo do veículo na vaga
    @Column(name = "modelo", nullable = false, length = 45)
    private String modelo;

    // Cor do veículo na vaga
    @Column(name = "cor", nullable = false, length = 45)
    private String cor;

    // Data de entrada do veículo na vaga
    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    // Data de saída do veículo da vaga
    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    // Valor cobrado pela vaga
    @Column(name = "valor", columnDefinition = "decimal(7,2)")
    private BigDecimal valor;

    // Valor do desconto aplicado à vaga
    @Column(name = "desconto", columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;

    // Cliente associado à vaga
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Vaga associada ao cliente
    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

    // Data de criação do registro (audit)
    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    // Data da última modificação do registro (audit)
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;

    // Usuário que criou o registro (audit)
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;

    // Usuário que modificou o registro pela última vez (audit)
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    // Método equals para comparar objetos ClienteVaga
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteVaga that = (ClienteVaga) o;
        return Objects.equals(id, that.id);
    }

    // Método hashCode para calcular o hash do objeto ClienteVaga
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
