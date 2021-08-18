package br.com.zupacademy.thiago.mercadolivre.produto;

import br.com.zupacademy.thiago.mercadolivre.caracteristica.CaracteristicaResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class DetalhesProdutoResponse {

    private Set<String> links;
    private String nome;
    private BigDecimal valor;
    private List<CaracteristicaResponse> caracteristicas;
    private String descricao;
    private int mediaDasNotas;
    private int totalDeNotas;
    private List<String> opinioes;
    private List<String> perguntas;

    public DetalhesProdutoResponse(Set<String> links, String nome, BigDecimal valor,
                   List<CaracteristicaResponse> caracteristicas, String descricao, int mediaDasNotas, int totalDeNotas,
                       List<String> opinioes, List<String> perguntas) {
        this.links = links;
        this.nome = nome;
        this.valor = valor;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.mediaDasNotas = mediaDasNotas;
        this.totalDeNotas = totalDeNotas;
        this.opinioes = opinioes;
        this.perguntas = perguntas;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Set<String> getLinks() {
        return links;
    }

    public List<CaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getOpinioes() {
        return opinioes;
    }

    public List<String> getPerguntas() {
        return perguntas;
    }

    public int getTotalDeNotas() {
        return totalDeNotas;
    }

    public int getMediaDasNotas() {
        return mediaDasNotas;
    }
}
