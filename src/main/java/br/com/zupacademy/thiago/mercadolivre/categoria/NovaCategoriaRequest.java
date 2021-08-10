package br.com.zupacademy.thiago.mercadolivre.categoria;

import br.com.zupacademy.thiago.mercadolivre.validator.IdExists;
import br.com.zupacademy.thiago.mercadolivre.validator.UniqueValue;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class NovaCategoriaRequest {

    @NotBlank
    @UniqueValue(modelo = Categoria.class, campo = "nome")
    private String nome;

    @IdExists(modelo = Categoria.class, campo = "id")
    private Long categoriaMae;

    public NovaCategoriaRequest(String nome, Long categoriaMae) {
        this.nome = nome;
        this.categoriaMae = categoriaMae;
    }

    public Categoria toCategoria(CategoriaRepository categoriaRepository) {

        Categoria categoria = new Categoria(nome);

        if (categoriaMae == null) {
            return categoria;
        }

        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaMae);
        categoria.setCategoriaMae(optionalCategoria.get());

        return categoria;
    }
}
