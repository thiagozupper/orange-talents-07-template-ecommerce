package br.com.zupacademy.thiago.mercadolivre.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SenhaTextoSimples {

    @NotBlank
    @Size(min = 6)
    private String texto;

    public SenhaTextoSimples(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return this.texto;
    }

    public String encode() {
        return new BCryptPasswordEncoder().encode(texto);
    }
}
