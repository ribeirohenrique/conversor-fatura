package entities;

public class FaturaInter {
    private String dataCompra;
    private String nomeLocal;
    private String valorCompra;

    public FaturaInter(String dataCompra, String nomeLocal, String valorCompra) {
        this.dataCompra = dataCompra;
        this.nomeLocal = nomeLocal;
        this.valorCompra = valorCompra;
    }

    public FaturaInter() {
    }

    @Override
    public String toString() {
        return dataCompra + ";" + nomeLocal + ";"+ valorCompra;
    }
}