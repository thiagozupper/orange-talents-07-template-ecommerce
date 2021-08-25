package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.pergunta.NovaPerguntaRequest;
import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class EmailSenderFake implements EmailSender {
    @Override
    public void envia(Usuario usuario, String mensagem) {

        System.out.println("Enviando email para: " + usuario.getUsername()
            + " com a mensagem: " + mensagem);
    }
}
