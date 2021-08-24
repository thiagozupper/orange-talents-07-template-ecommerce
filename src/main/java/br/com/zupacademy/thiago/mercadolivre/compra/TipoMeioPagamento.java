package br.com.zupacademy.thiago.mercadolivre.compra;

public enum TipoMeioPagamento {

    PAYPAL(1) {
        @Override
        public MeioPagamento getMeioPagamento() {
            return new MeioPagamentoPaypal();
        }
    },
    PAGSEGURO(2) {
        @Override
        public MeioPagamento getMeioPagamento() {
            return new MeioPagamentoPagSeguro();
        }
    };

    private int codigo;

    private TipoMeioPagamento(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoMeioPagamento toEnum(int codigo) {

        for (TipoMeioPagamento tipo : TipoMeioPagamento.values()) {
            if (tipo.codigo == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException();
    }

    public abstract MeioPagamento getMeioPagamento();
}
