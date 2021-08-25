package br.com.zupacademy.thiago.mercadolivre.transacao;

import br.com.zupacademy.thiago.mercadolivre.compra.Compra;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Compra compra;

    private Long idTransacaoGateway;

    private int status;

    private LocalDateTime instanteConfirmacaoPagamento;

    @Deprecated
    public Transacao() {
    }

    public Transacao(Compra compra, Long idTransacaoGateway, StatusTransacao status,
                     LocalDateTime instanteConfirmacaoPagamento) {
        this.compra = compra;
        this.idTransacaoGateway = idTransacaoGateway;
        this.status = status.getCodigo();
        this.instanteConfirmacaoPagamento = instanteConfirmacaoPagamento;
    }

    public boolean concluidaComSucesso() {
        return this.status == StatusTransacao.SUCESSO.getCodigo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(idTransacaoGateway, transacao.idTransacaoGateway);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransacaoGateway);
    }
}
