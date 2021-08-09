package br.com.zupacademy.thiago.mercadolivre.usuario;

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
    private String login;

    @NotNull
    @Valid
    private SenhaTextoSimples senha;

    @NotNull
    @PastOrPresent
    private LocalDateTime dataCadastro;

    public NovoUsuarioRequest(String login, SenhaTextoSimples senha) {
        this.login = login;
        this.senha = senha;
    }

    public SenhaTextoSimples getSenha() {
        return senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public Usuario toUsuario() {
        return new Usuario(login, senha, dataCadastro);
    }
}
