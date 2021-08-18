package br.com.zupacademy.thiago.mercadolivre.pergunta;

import javax.validation.constraints.NotBlank;

public class NovaPerguntaRequest {

    @NotBlank
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "NovaPerguntaRequest{" +
                "titulo='" + titulo + '\'' +
                '}';
    }
}
