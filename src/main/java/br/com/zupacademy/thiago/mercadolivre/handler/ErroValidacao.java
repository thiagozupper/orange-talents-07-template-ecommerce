package br.com.zupacademy.thiago.mercadolivre.handler;

public class ErroValidacao {
    private String campo;
    private String erro;

    public ErroValidacao(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
