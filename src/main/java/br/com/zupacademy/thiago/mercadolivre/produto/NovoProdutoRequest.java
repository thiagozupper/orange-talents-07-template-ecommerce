package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.caracteristica.NovaCaracteristicaRequest;
import br.com.zupacademy.thiago.mercadolivre.categoria.Categoria;
import br.com.zupacademy.thiago.mercadolivre.categoria.CategoriaRepository;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import br.com.zupacademy.thiago.mercadolivre.validator.IdExists;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class NovoProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private int quantidadeDisponivel;

    @NotNull
    @Size(min = 3)
    @Valid
    private List<NovaCaracteristicaRequest> caracteristicas;

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotNull
    @IdExists(modelo = Categoria.class, campo = "id")
    private Long categoriaId;

    public NovoProdutoRequest(String nome, BigDecimal valor,
                              int quantidadeDisponivel, List<NovaCaracteristicaRequest> caracteristicas,
                              String descricao, Long categoriaId) {
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
    }

    public List<NovaCaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public Produto toProduto(CategoriaRepository categoriaRepository, Usuario usuario) {

        Categoria categoria = categoriaRepository.findById(categoriaId).get();

        Produto produto = new Produto(nome, valor, quantidadeDisponivel, caracteristicas,
                descricao, categoria, usuario, LocalDateTime.now());

        return produto;
    }
}
