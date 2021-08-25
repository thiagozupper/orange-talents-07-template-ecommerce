package br.com.zupacademy.thiago.mercadolivre.transacao;

import br.com.zupacademy.thiago.mercadolivre.compra.Compra;
import br.com.zupacademy.thiago.mercadolivre.compra.CompraRepository;
import br.com.zupacademy.thiago.mercadolivre.produto.EmailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pagamentos")
public class TransacaoController {

    private final CompraRepository compraRepository;
    private final EmailSender emailSender;

    public TransacaoController(CompraRepository compraRepository, EmailSender emailSender) {
        this.compraRepository = compraRepository;
        this.emailSender = emailSender;
    }

    @PostMapping("/confirmacao-pagamento")
    public ResponseEntity<?> confirmacaoPagamento (
            @RequestBody @Valid RetornoGatewayRequest request) {

        Compra compra = compraRepository.findById(request.getCompraId()).get();
        compra.adicionaTransacao(request);
        compra.atualizaStatus(request);
        compraRepository.save(compra);

        if (compra.processadaComSucesso()) {
            comunicaComNotaFiscal(compra);
            comunicaComSistemaDeRanking(compra);
            emailSender.envia(compra.getComprador(), "Sua compra foi efetuada com sucesso.");
        } else {
            emailSender.envia(compra.getComprador(), "Houve uma falha ao processar sua compra.");
        }

        return ResponseEntity.ok(request.getStatus());
    }

    private void comunicaComNotaFiscal(Compra compra) {
        System.out.println("Compra: " + compra.getId() + ", Usuário: " + compra.getComprador().getId());
    }

    private void comunicaComSistemaDeRanking(Compra compra) {
        System.out.println("Compra: " + compra.getId() + ", Usuário: " + compra.getProduto().getUsuario().getId());
    }
}
