package br.com.zupacademy.thiago.mercadolivre.compra;

import java.net.URI;
import java.net.URISyntaxException;

public interface MeioPagamento {

    public String redirectTo(Compra compra);
}
