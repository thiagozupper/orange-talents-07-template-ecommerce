package br.com.zupacademy.thiago.mercadolivre.compra;

import java.net.URI;

public class MeioPagamentoPagSeguro implements MeioPagamento {
    @Override
    public String redirectTo(Compra compra) {
        return  "https://pagseguro.com?buyerId=" + compra.getId() +
                "&redirectUrl=http://localhost:8080/compras/confirmacao";
    }
}
