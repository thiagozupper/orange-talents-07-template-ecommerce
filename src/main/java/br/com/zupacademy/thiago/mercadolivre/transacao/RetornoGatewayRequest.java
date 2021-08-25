package br.com.zupacademy.thiago.mercadolivre.transacao;

import br.com.zupacademy.thiago.mercadolivre.compra.Compra;
import br.com.zupacademy.thiago.mercadolivre.validator.CompraFinalizadaComSucesso;
import br.com.zupacademy.thiago.mercadolivre.validator.TransacaoJaProcessada;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RetornoGatewayRequest {

    @NotNull
    @CompraFinalizadaComSucesso
    private Long compraId;

    @NotNull
    @TransacaoJaProcessada
    private Long idTransacaoGateway;

    @NotEmpty
    private String status;

    public RetornoGatewayRequest(Long compraId, Long idTransacaoGateway, String status) {
        this.compraId = compraId;
        this.idTransacaoGateway = idTransacaoGateway;
        this.status = status;
    }

    public Long getCompraId() {
        return compraId;
    }

    public Long getIdTransacaoGateway() {
        return idTransacaoGateway;
    }

    public String getStatus() {
        return status;
    }

    public Transacao toTransacao(Compra compra) {
        StatusTransacao statusPagamento = StatusTransacao.toEnum(status);
        return new Transacao(compra, idTransacaoGateway, statusPagamento, LocalDateTime.now());
    }
}
