package br.com.zupacademy.thiago.mercadolivre.transacao;

public enum StatusTransacao {

    ERRO(0, "ERRO"),
    SUCESSO(1, "SUCESSO");

    StatusTransacao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    private int codigo;
    private String descricao;

    public int getCodigo() {
        return codigo;
    }

    public static StatusTransacao toEnum(int codigo) {
        for (StatusTransacao statusPagamento : StatusTransacao.values()) {
            if (statusPagamento.codigo == codigo) {
                return statusPagamento;
            }
        }
        throw new IllegalArgumentException();
    }

    public static StatusTransacao toEnumByDescricao(String descricao) {
        for (StatusTransacao statusPagamento : StatusTransacao.values()) {
            if (statusPagamento.descricao.equals(descricao)) {
                return statusPagamento;
            }
        }
        throw new IllegalArgumentException();
    }

    public static StatusTransacao toEnum(String statusRetorno) {
        if (statusRetorno.equals("SUCESSO") || statusRetorno.equals("ERRO")) {
            return toEnumByDescricao(statusRetorno);
        } else if (Integer.parseInt(statusRetorno) == SUCESSO.getCodigo() || Integer.parseInt(statusRetorno) == ERRO.getCodigo()){
            return toEnum(Integer.parseInt(statusRetorno));
        }
        throw new IllegalArgumentException();
    }

}
