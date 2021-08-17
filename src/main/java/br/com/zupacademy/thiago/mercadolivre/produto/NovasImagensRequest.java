package br.com.zupacademy.thiago.mercadolivre.produto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class NovasImagensRequest {

    @NotNull
    @Size(min = 1)
    private Set<MultipartFile> imagens = new HashSet<>();

    public void setImagens(Set<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public Set<MultipartFile> getImagens() {
        return this.imagens;
    }
}
