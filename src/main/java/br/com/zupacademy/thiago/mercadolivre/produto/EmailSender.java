package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;

public interface EmailSender {

    public void envia(Usuario usuario, String mensagem);
}
