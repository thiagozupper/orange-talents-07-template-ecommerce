package br.com.zupacademy.thiago.mercadolivre.usuario;

import br.com.zupacademy.thiago.mercadolivre.validator.UniqueEmail;
import org.mindrot.jbcrypt.BCrypt;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

public class NovoUsuarioRequest {

    @NotBlank
    @Email
    @UniqueEmail(modelo = Usuario.class, campo = "login")
    private String login;

    @NotNull
    @Valid
    private SenhaTextoSimples senha;

    public NovoUsuarioRequest(String login, SenhaTextoSimples senha) {
        this.login = login;
        this.senha = senha;
    }

    public SenhaTextoSimples getSenha() {
        return senha;
    }

    public Usuario toUsuario() {
        return new Usuario(login, senha, LocalDateTime.now());
    }
}
