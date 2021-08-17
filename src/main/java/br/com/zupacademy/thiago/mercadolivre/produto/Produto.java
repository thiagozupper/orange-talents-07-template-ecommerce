package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.caracteristica.Caracteristica;
import br.com.zupacademy.thiago.mercadolivre.caracteristica.NovaCaracteristicaRequest;
import br.com.zupacademy.thiago.mercadolivre.categoria.Categoria;
import br.com.zupacademy.thiago.mercadolivre.opiniao.Opiniao;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private List<Opiniao> opinioes = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    public Produto() {
    }

    public Produto(String nome, BigDecimal valor, int quantidadeDisponivel,
                   List<NovaCaracteristicaRequest> caracteristicas, String descricao,
                   Categoria categoria, Usuario usuario, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.caracteristicas.addAll(caracteristicas.stream()
                                .map(c -> new Caracteristica(c, this))
                                .collect(Collectors.toList()));
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.dataCadastro = dataCadastro;
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

    public boolean pertenceA(Usuario usuarioLogado) {
        return this.usuario.equals(usuarioLogado);
    }


    public void associarLinks(Set<String> links) {

        Set<ImagemProduto> imagens = links.stream()
                .map(link -> new ImagemProduto(this, link))
                .collect(Collectors.toSet());
        this.imagens.addAll(imagens);
    }

    public void associarOpiniao(NovaOpiniaoRequest novaOpiniao, Usuario usuario) {
        Opiniao opiniao = new Opiniao(novaOpiniao, usuario, this);
        this.opinioes.add(opiniao);
    }
}
