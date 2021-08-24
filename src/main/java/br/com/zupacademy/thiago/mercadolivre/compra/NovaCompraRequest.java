package br.com.zupacademy.thiago.mercadolivre.compra;

import br.com.zupacademy.thiago.mercadolivre.produto.Produto;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import br.com.zupacademy.thiago.mercadolivre.validator.TemEstoque;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@TemEstoque
public class NovaCompraRequest {

    @NotNull
    private Long produtoId;

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    private Integer meioPagamento;

    public NovaCompraRequest(Long produtoId, Integer quantidade, Integer meioPagamento) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.meioPagamento = meioPagamento;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Integer getMeioPagamento() {
        return meioPagamento;
    }


    public Compra toCompra(Produto produto, TipoMeioPagamento tipoMeioPagamento, Usuario comprador) {
        return new Compra(produto, tipoMeioPagamento, this.quantidade, comprador);
    }
}
