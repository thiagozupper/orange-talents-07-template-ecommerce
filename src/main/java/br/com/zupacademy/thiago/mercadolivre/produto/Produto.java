package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.caracteristica.Caracteristica;
import br.com.zupacademy.thiago.mercadolivre.categoria.Categoria;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Positive
    @Column(nullable = false)
    private BigDecimal valor;

    @PositiveOrZero
    @Column(nullable = false)
    private int quantidadeDisponivel;

    @Size(min = 3)
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private List<Caracteristica> caracteristicas = new ArrayList<Caracteristica>();

    @Size(max = 1000)
    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    private Categoria categoria;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    public Produto() {
    }

    public Produto(String nome, BigDecimal valor, int quantidadeDisponivel,
                   List<Caracteristica> caracteristicas, String descricao,
                   Categoria categoria, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.caracteristicas.addAll(caracteristicas);
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataCadastro = dataCadastro;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
