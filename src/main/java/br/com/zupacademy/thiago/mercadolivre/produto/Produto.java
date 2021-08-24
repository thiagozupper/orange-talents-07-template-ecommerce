package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.caracteristica.Caracteristica;
import br.com.zupacademy.thiago.mercadolivre.caracteristica.CaracteristicaResponse;
import br.com.zupacademy.thiago.mercadolivre.caracteristica.NovaCaracteristicaRequest;
import br.com.zupacademy.thiago.mercadolivre.categoria.Categoria;
import br.com.zupacademy.thiago.mercadolivre.opiniao.NovaOpiniaoRequest;
import br.com.zupacademy.thiago.mercadolivre.opiniao.Opiniao;
import br.com.zupacademy.thiago.mercadolivre.pergunta.NovaPerguntaRequest;
import br.com.zupacademy.thiago.mercadolivre.pergunta.Pergunta;
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

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private List<Pergunta> perguntas = new ArrayList<>();

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

    public Usuario getUsuario() {
        return usuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void associarOpiniao(NovaOpiniaoRequest novaOpiniao, Usuario usuario) {
        Opiniao opiniao = new Opiniao(novaOpiniao, usuario, this);
        this.opinioes.add(opiniao);
    }

    public void associarPergunta(NovaPerguntaRequest novaPergunta, Usuario autorPergunta) {
        Pergunta pergunta = new Pergunta(novaPergunta, autorPergunta, this);
        this.perguntas.add(pergunta);
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

    public DetalhesProdutoResponse toDetalhesProdutoResponse() {

        Set<String> links = this.imagens.stream()
                .map(imagemProduto -> imagemProduto.getLink())
                .collect(Collectors.toSet());

        List<CaracteristicaResponse> caracteristicas = this.caracteristicas.stream()
                .map(c -> c.toCaracteristicaResponse())
                .collect(Collectors.toList());

        List<Integer> notas = this.opinioes.stream().map(o -> o.getNota()).collect(Collectors.toList());
        Integer somatorioNotas = notas.stream().reduce(0, (subtotal, notal) -> subtotal + notal);
        Integer mediaDasNotas = somatorioNotas / notas.size();

        List<String> opinioes = this.opinioes.stream().map(o -> o.getTitulo()).collect(Collectors.toList());
        List<String> perguntas = this.perguntas.stream().map(p -> p.getTitulo()).collect(Collectors.toList());

        return new DetalhesProdutoResponse(links, this.nome, this.valor,
                caracteristicas, this.descricao, mediaDasNotas, notas.size(), opinioes, perguntas);
    }

    public void darBaixaEstoque(int quantidade) {
        this.quantidadeDisponivel -= quantidade;
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
