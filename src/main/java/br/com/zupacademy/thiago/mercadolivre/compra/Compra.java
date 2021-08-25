package br.com.zupacademy.thiago.mercadolivre.compra;

import br.com.zupacademy.thiago.mercadolivre.produto.Produto;
import br.com.zupacademy.thiago.mercadolivre.transacao.RetornoGatewayRequest;
import br.com.zupacademy.thiago.mercadolivre.transacao.StatusTransacao;
import br.com.zupacademy.thiago.mercadolivre.transacao.Transacao;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Produto produto;

    private int meioPagamento;

    private int quantidade;

    @ManyToOne
    private Usuario comprador;

    private BigDecimal valorProduto;

    private int statusCompra = StatusCompra.INICIADA.getCodigo();

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    public Compra() {}

    public Compra(Produto produto, TipoMeioPagamento tipoMeioPagamento, int quantidade, Usuario comprador) {
        this.produto = produto;
        this.meioPagamento = tipoMeioPagamento.getCodigo();
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.valorProduto = produto.getValor();
    }

    public Long getId() {
        return id;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Produto getProduto() {
        return produto;
    }

    public void adicionaTransacao(RetornoGatewayRequest request) {

        Transacao transacao = request.toTransacao(this);

        Assert.isTrue(!this.transacoes.contains(transacao), "Essa transação já foi processada antes.");

        Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet());

        Assert.isTrue(transacoesConcluidasComSucesso.isEmpty(), "Essa compra já foi processada com sucesso");

        this.transacoes.add(transacao);
    }

    public void atualizaStatus(RetornoGatewayRequest request) {
        StatusTransacao statusTransacao = StatusTransacao.toEnum(request.getStatus());
        if (statusTransacao.equals(StatusTransacao.SUCESSO)) {
            this.statusCompra = StatusCompra.FINALIZADA_COM_SUCESSO.getCodigo();
        }
    }

    public boolean processadaComSucesso() {
        return this.statusCompra == StatusCompra.FINALIZADA_COM_SUCESSO.getCodigo();
    }
}
