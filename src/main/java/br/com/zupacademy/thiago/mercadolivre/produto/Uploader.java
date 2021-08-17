package br.com.zupacademy.thiago.mercadolivre.produto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface Uploader {

    Set<String> envia(Set<MultipartFile> imagens);
}
