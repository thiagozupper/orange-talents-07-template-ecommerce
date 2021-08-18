package br.com.zupacademy.thiago.mercadolivre.pergunta;

import br.com.zupacademy.thiago.mercadolivre.produto.Produto;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity

public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Usuario usuario;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Pergunta() {}

    public Pergunta(NovaPerguntaRequest novaPergunta, Usuario autorPergunta, Produto produto) {
        this.titulo = novaPergunta.getTitulo();
        this.produto = produto;
        this.usuario = autorPergunta;
    }

    public String getTitulo() {
        return titulo;
    }
}
