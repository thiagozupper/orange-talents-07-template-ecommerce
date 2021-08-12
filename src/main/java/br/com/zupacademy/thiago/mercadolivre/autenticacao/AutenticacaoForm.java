package br.com.zupacademy.thiago.mercadolivre.autenticacao;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class AutenticacaoForm {

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    public AutenticacaoForm(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "AutenticacaoForm{" +
                "login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public UsernamePasswordAuthenticationToken dadosLogin() {
        return new UsernamePasswordAuthenticationToken(login, senha);
    }
}
