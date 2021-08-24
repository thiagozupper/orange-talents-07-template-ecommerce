package br.com.zupacademy.thiago.mercadolivre.compra;

public enum StatusCompra {

    INICIADA(1);

    private int codigo;

    StatusCompra(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusCompra toEnum(int codigo) {
        for (StatusCompra statusCompra : StatusCompra.values()) {
            if (statusCompra.codigo == codigo) {
                return statusCompra;
            }
        }
        throw new IllegalArgumentException();
    }
}

