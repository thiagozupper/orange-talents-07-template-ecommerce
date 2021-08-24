package br.com.zupacademy.thiago.mercadolivre.compra;

import br.com.zupacademy.thiago.mercadolivre.produto.Produto;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import java.math.BigDecimal;

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
}
