package entities;

public class Fatura {
    private String tipoCompra;
    private String dataCompra;
    private String nomeLocal;
    private String numParcelas;
    private String valorCompra;

    public Fatura(String tipoCompra, String dataCompra, String nomeLocal, String valorCompra, String numParcelas) {
        this.tipoCompra = tipoCompra;
        this.dataCompra = dataCompra;
        this.nomeLocal = nomeLocal;
        this.valorCompra = valorCompra;
        this.numParcelas = numParcelas;
    }

    public Fatura() {
    }

    @Override
    public String toString() {
        return tipoCompra + ";" + dataCompra + ";"+ nomeLocal + ";"+ valorCompra + ";"+ numParcelas;
    }
}
