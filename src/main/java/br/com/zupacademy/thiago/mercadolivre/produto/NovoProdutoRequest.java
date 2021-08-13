package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.caracteristica.Caracteristica;
import br.com.zupacademy.thiago.mercadolivre.categoria.Categoria;
import br.com.zupacademy.thiago.mercadolivre.categoria.CategoriaRepository;
import br.com.zupacademy.thiago.mercadolivre.validator.IdExists;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Map<@NotBlank String, @NotBlank String> caracteristicas;

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotNull
    @IdExists(modelo = Categoria.class, campo = "id")
    private Long categoriaId;

    public Map<String, String> getCaracteristicas() {
        return caracteristicas;
    }

    public NovoProdutoRequest(String nome, BigDecimal valor,
                              int quantidadeDisponivel, Map<String, String> caracteristicas,
                              String descricao, Long categoriaId) {
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
    }

    public Produto toProduto(CategoriaRepository categoriaRepository) {

        Categoria categoria = categoriaRepository.findById(categoriaId).get();

        List<Caracteristica> caracteristicas = new ArrayList<>();
        for (var entry : this.caracteristicas.entrySet()) {
            caracteristicas.add(new Caracteristica(entry.getKey(), entry.getValue()));
        }

        return new Produto(this.nome, this.valor,
                this.quantidadeDisponivel, caracteristicas,
                this.descricao, categoria, LocalDateTime.now());
    }
}
