package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.categoria.CategoriaRepository;
import br.com.zupacademy.thiago.mercadolivre.opiniao.NovaOpiniaoRequest;
import br.com.zupacademy.thiago.mercadolivre.pergunta.NovaPerguntaRequest;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;
    private Uploader uploader;
    private EmailSender emailSender;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
                             Uploader uploader, EmailSender emailSender) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.uploader = uploader;
        this.emailSender = emailSender;
    }

    @PostMapping
    public void cadastrar(@RequestBody @Valid NovoProdutoRequest dto, Principal principal) {
        Usuario usuario = getUsuarioLogado(principal);
        Produto produto = dto.toProduto(categoriaRepository, usuario);
        produtoRepository.save(produto);
    }

    @PostMapping("/{idProduto}/imagens")
    @Transactional
    public void uploadImagens(@PathVariable Long idProduto, @Valid NovasImagensRequest dto, Principal principal) {

        Usuario usuarioLogado = getUsuarioLogado(principal);
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
                                 @RequestBody @Valid NovaOpiniaoRequest novaOpiniao, Principal principal) {

        Produto produto = produtoRepository.findById(idProduto).get();
        Usuario usuario = getUsuarioLogado(principal);
        produto.associarOpiniao(novaOpiniao, usuario);
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
        emailSender.envia(donoProduto, novaPergunta);
    }

    private Usuario getUsuarioLogado(Principal principal) {
        UsernamePasswordAuthenticationToken autenticado = (UsernamePasswordAuthenticationToken) principal;
        Usuario usuario = (Usuario) autenticado.getPrincipal();
        return usuario;
    }

}
