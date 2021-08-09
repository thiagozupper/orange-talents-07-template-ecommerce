package br.com.zupacademy.thiago.mercadolivre.usuario;

import org.mindrot.jbcrypt.BCrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
        return BCrypt.hashpw(this.texto, BCrypt.gensalt());
    }
}
