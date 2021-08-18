package br.com.zupacademy.thiago.mercadolivre.opiniao;

import br.com.zupacademy.thiago.mercadolivre.produto.Produto;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @Min(1)
    @Max(5)
    private int nota;

    @JoinColumn(nullable = false)
    private String titulo;

    @JoinColumn(nullable = false)
    @Size(max = 500)
    private String descricao;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Produto produto;

    public Opiniao() {}

    public Opiniao(NovaOpiniaoRequest novaOpiniao, Usuario usuario, Produto produto) {
        this.nota = novaOpiniao.getNota();
        this.titulo = novaOpiniao.getTitulo();
        this.descricao = novaOpiniao.getDescricao();
        this.usuario = usuario;
        this.produto = produto;
    }
}
