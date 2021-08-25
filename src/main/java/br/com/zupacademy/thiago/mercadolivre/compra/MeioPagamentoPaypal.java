package br.com.zupacademy.thiago.mercadolivre.compra;

import java.net.URI;
import java.net.URISyntaxException;

public class MeioPagamentoPaypal implements MeioPagamento {
    @Override
    public String redirectTo(Compra compra) {
        return  "https://paypal.com?compraId=" + compra.getId() +
                "&retornoUrl=http://localhost:8080/api/pagamentos/confirmacao-pagamento";
    }
}
