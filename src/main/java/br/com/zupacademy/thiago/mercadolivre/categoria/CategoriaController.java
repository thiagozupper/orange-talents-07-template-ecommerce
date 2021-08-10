package br.com.zupacademy.thiago.mercadolivre.categoria;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    public void cadastrar(@RequestBody @Valid NovaCategoriaRequest dto) {
        Categoria categoria = dto.toCategoria(categoriaRepository);
        categoriaRepository.save(categoria);
    }
}
