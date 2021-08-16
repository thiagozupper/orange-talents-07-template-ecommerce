package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.categoria.CategoriaRepository;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    public void cadastrar(@RequestBody @Valid NovoProdutoRequest dto, Principal principal) {
        Usuario usuario = getUsuarioLogado(principal);
        Produto produto = dto.toProduto(categoriaRepository, usuario);
        produtoRepository.save(produto);
    }

    @PostMapping("/imagens")
    public ResponseEntity<?> uploadImagens(@Valid UploadImagensProdutoRequest dto, Principal principal) {

        Produto produto = produtoRepository.findById(dto.getIdProduto()).get();
        Usuario usuario = getUsuarioLogado(principal);

        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Você não tem permissão para alterar esse produto");
        }

        List<String> links = Arrays.stream(dto.getImagens())
                .map(imagem -> "http://ml-storage/" + imagem.getOriginalFilename())
                .collect(Collectors.toList());

        produto.getImagensLinks().addAll(links);
        produtoRepository.save(produto);
        return ResponseEntity.ok().build();
    }

    private Usuario getUsuarioLogado(Principal principal) {
        UsernamePasswordAuthenticationToken autenticado = (UsernamePasswordAuthenticationToken) principal;
        Usuario usuario = (Usuario) autenticado.getPrincipal();
        return usuario;
    }

}
