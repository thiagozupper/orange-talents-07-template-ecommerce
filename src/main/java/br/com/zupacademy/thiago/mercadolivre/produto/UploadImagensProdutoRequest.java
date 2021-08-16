package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.validator.IdExists;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UploadImagensProdutoRequest {

    @NotNull
    @IdExists(modelo = Produto.class, campo = "id")
    private Long idProduto;

    @NotNull
    @Size(min = 2)
    private MultipartFile[] imagens;

    public UploadImagensProdutoRequest(Long idProduto, MultipartFile[] imagens) {
        this.idProduto = idProduto;
        this.imagens = imagens;
    }

    public MultipartFile[] getImagens() {
        return imagens;
    }

    public Long getIdProduto() {
        return idProduto;
    }
}
