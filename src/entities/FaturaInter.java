package entities;

public class FaturaInter {
    private final String dataCompra;
    private final String nomeLocal;
    private final String numParcelas;
    private final String valorCompra;

    public FaturaInter(String dataCompra, String nomeLocal, String numParcelas, String valorCompra) {
        this.dataCompra = dataCompra;
        this.nomeLocal = nomeLocal;
        this.numParcelas = numParcelas;
        this.valorCompra = valorCompra;
    }

    @Override
    public String toString() {
        return dataCompra + ";" + nomeLocal.toUpperCase() + ";" + numParcelas + ";" + valorCompra;
    }
}
