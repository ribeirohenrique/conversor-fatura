import entities.FaturaItau;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConverteFaturaItau {
    public static void main(String[] args) {
        List<FaturaItau> faturaList = new ArrayList<>();
        String path = "src/faturaItau.txt"; // Arquivo de entrada

        // Pega o ano e mês atuais para a lógica de data
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1; // Janeiro é 0, então somamos 1

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String linhaFatura = bufferedReader.readLine();
            while (linhaFatura != null) {
                String regexComParcela = "^(\\d{2}/\\d{2})\\s+(.+?)\\s*(\\d{1,2}/\\d{1,2})\\s+([\\d,.]+)$";
                Pattern patternComParcela = Pattern.compile(regexComParcela);
                Matcher matcherComParcela = patternComParcela.matcher(linhaFatura);

                String regexSemParcela = "^(\\d{2}/\\d{2})\\s+(.+?)\\s+([\\d,.]+)$";
                Pattern patternSemParcela = Pattern.compile(regexSemParcela);
                Matcher matcherSemParcela = patternSemParcela.matcher(linhaFatura);

                String dataCompleta = "";
                String descricao = "";
                String parcelas = "-";
                String valor = "";
                boolean matchFound = false;

                if (matcherComParcela.find()) {
                    String dataCurta = matcherComParcela.group(1); // "dd/MM"
                    descricao = matcherComParcela.group(2).trim();
                    parcelas = matcherComParcela.group(3);
                    valor = matcherComParcela.group(4);

                    int mesCompra = Integer.parseInt(dataCurta.split("/")[1]);
                    int parcelaAtual = Integer.parseInt(parcelas.split("/")[0]);

                    // LÓGICA MELHORADA: A compra é do ano passado se:
                    // 1. O mês da compra for maior que o mês atual, OU
                    // 2. O número da parcela atual for maior que o mês atual.
                    int anoCompra = (mesCompra > mesAtual || parcelaAtual > mesAtual) ? (anoAtual - 1) : anoAtual;
                    dataCompleta = dataCurta + "/" + anoCompra;

                    matchFound = true;

                } else if (matcherSemParcela.find()) {
                    String dataCurta = matcherSemParcela.group(1); // "dd/MM"
                    descricao = matcherSemParcela.group(2).trim();
                    valor = matcherSemParcela.group(3);

                    int mesCompra = Integer.parseInt(dataCurta.split("/")[1]);

                    // Para compras sem parcela, a lógica antiga se mantém
                    int anoCompra = (mesCompra > mesAtual) ? (anoAtual - 1) : anoAtual;
                    dataCompleta = dataCurta + "/" + anoCompra;

                    matchFound = true;
                }

                if (matchFound) {
                    faturaList.add(new FaturaItau(dataCompleta, descricao, parcelas, valor));
                } else {
                    System.out.println("A linha não corresponde ao padrão esperado.");
                    System.out.println("Linha: " + linhaFatura);
                }
                linhaFatura = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
        Date date = new Date();
        String arquivoSaida = "src\\faturaItauConvertida_" + sdf.format(date) + ".csv";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivoSaida))) {
            bufferedWriter.write("dataCompra;descricao;numParcelas;valorCompra\n");
            for (FaturaItau fatura : faturaList) {
                bufferedWriter.write(fatura.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Conversão de fatura Itaú efetuada com sucesso.");
    }
}