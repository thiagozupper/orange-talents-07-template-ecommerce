package br.com.zupacademy.thiago.mercadolivre.produto;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFake implements Uploader {

    @Override
    public Set<String> envia(Set<MultipartFile> imagens) {

        Set<String> links = imagens.stream()
                                .map(imagem -> "http://ml-storage/" + imagem.getOriginalFilename())
                                .collect(Collectors.toSet());
        return links;
    }
}
