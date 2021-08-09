package br.com.zupacademy.thiago.mercadolivre.usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    /**
     * Apenas para uso do Hibernate
     */
    @Deprecated
    public Usuario() {
    }

    public Usuario(
            @NotBlank String login,
            @NotBlank SenhaTextoSimples senha,
            @NotBlank @PastOrPresent LocalDateTime dataCadastro) {
        this.login = login;
        this.senha = senha.encode();
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
