package br.com.zupacademy.thiago.mercadolivre.caracteristica;

public class CaracteristicaResponse {

    private String nome;
    private String descricao;

    public CaracteristicaResponse(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
