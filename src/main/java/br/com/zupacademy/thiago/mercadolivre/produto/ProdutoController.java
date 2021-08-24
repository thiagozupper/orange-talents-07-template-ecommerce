package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.categoria.CategoriaRepository;
import br.com.zupacademy.thiago.mercadolivre.opiniao.NovaOpiniaoRequest;
import br.com.zupacademy.thiago.mercadolivre.pergunta.NovaPerguntaRequest;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final Uploader uploader;
    private final EmailSender emailSender;

    public ProdutoController(ProdutoRepository produtoRepository,
                             CategoriaRepository categoriaRepository,
                             Uploader uploader,
                             EmailSender emailSender) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.uploader = uploader;
        this.emailSender = emailSender;
    }

    @PostMapping
    public void cadastrar(@RequestBody @Valid NovoProdutoRequest dto, @AuthenticationPrincipal Usuario usuario) {
        Produto produto = dto.toProduto(categoriaRepository, usuario);
        produtoRepository.save(produto);
    }

    @PostMapping("/{idProduto}/imagens")
    @Transactional
    public void uploadImagens(@PathVariable Long idProduto, @Valid NovasImagensRequest dto,
                              @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = produtoRepository.findById(idProduto).get();
        if (!produto.pertenceA(usuarioLogado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = uploader.envia(dto.getImagens());
        produto.associarLinks(links);
        produtoRepository.save(produto);
    }

    @PostMapping("/{idProduto}/opinioes")
    @Transactional
    public void cadastrarOpiniao(@PathVariable Long idProduto,
                                 @RequestBody @Valid NovaOpiniaoRequest novaOpiniao,
                                 @AuthenticationPrincipal Usuario autorOpiniao) {

        Produto produto = produtoRepository.findById(idProduto).get();
        produto.associarOpiniao(novaOpiniao, autorOpiniao);
        produtoRepository.save(produto);
    }

    @PostMapping("/{idProduto}/perguntas")
    @Transactional
    public void cadastrarPergunta(@PathVariable Long idProduto,
                                @RequestBody @Valid NovaPerguntaRequest novaPergunta,
                                @AuthenticationPrincipal Usuario autorPergunta) {

        Produto produto = produtoRepository.findById(idProduto).get();
        produto.associarPergunta(novaPergunta, autorPergunta);
        produtoRepository.save(produto);

        Usuario donoProduto = produto.getUsuario();
        emailSender.envia(donoProduto, novaPergunta.getTitulo());
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<DetalhesProdutoResponse> detalhesProduto(@PathVariable Long idProduto) {

        Optional<Produto> optionalProduto = produtoRepository.findById(idProduto);
        Produto produto = optionalProduto.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST)
        );
        return ResponseEntity.ok(produto.toDetalhesProdutoResponse());
    }

}
