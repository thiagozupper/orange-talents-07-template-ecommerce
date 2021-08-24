package br.com.zupacademy.thiago.mercadolivre.compra;

import br.com.zupacademy.thiago.mercadolivre.produto.EmailSender;
import br.com.zupacademy.thiago.mercadolivre.produto.Produto;
import br.com.zupacademy.thiago.mercadolivre.produto.ProdutoRepository;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private ProdutoRepository produtoRepository;
    private CompraRepository compraRepository;
    private EmailSender emailSender;

    public CompraController(ProdutoRepository produtoRepository, CompraRepository compraRepository, EmailSender emailSender) {
        this.produtoRepository = produtoRepository;
        this.compraRepository = compraRepository;
        this.emailSender = emailSender;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> novaCompra(@AuthenticationPrincipal Usuario comprador,
                           @RequestBody @Valid NovaCompraRequest novaCompra) {

        Produto produto = produtoRepository.findById(novaCompra.getProdutoId()).get();
        TipoMeioPagamento tipoMeioPagamento = TipoMeioPagamento.toEnum(novaCompra.getMeioPagamento());

        Compra compra = novaCompra.toCompra(produto, tipoMeioPagamento, comprador);
        compraRepository.save(compra);

        produto.darBaixaEstoque(novaCompra.getQuantidade());

        emailSender.envia(produto.getUsuario(), "Nova compra para um de seus produtos");

        MeioPagamento meioPagamento = tipoMeioPagamento.getMeioPagamento();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(meioPagamento.redirectTo(compra))).build();
    }
}
