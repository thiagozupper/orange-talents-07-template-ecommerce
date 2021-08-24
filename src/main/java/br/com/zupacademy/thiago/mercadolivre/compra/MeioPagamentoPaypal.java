package br.com.zupacademy.thiago.mercadolivre.compra;

import java.net.URI;
import java.net.URISyntaxException;

public class MeioPagamentoPaypal implements MeioPagamento {
    @Override
    public String redirectTo(Compra compra) {
        return  "https://paypal.com?buyerId=" + compra.getId() +
                "&redirectUrl=http://localhost:8080/compras/confirmacao";
    }
}
