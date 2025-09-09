package entities;

public class FaturaItau {
    private final String dataCompra;
    private final String descricao;
    private final String numParcelas;
    private final String valorCompra;

    public FaturaItau(String dataCompra, String descricao, String numParcelas, String valorCompra) {
        this.dataCompra = dataCompra;
        this.descricao = descricao;
        this.numParcelas = numParcelas;
        this.valorCompra = valorCompra;
    }

    @Override
    public String toString() {
        // Formata a saída para o CSV, trocando "/" por "|" nas parcelas para manter o padrão
        return dataCompra + ";" + descricao.toUpperCase().trim() + ";" + numParcelas.replace("/", "|") + ";" + valorCompra;
    }
}