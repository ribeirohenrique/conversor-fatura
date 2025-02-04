import entities.FaturaInter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConverteFaturaInter {
    public static void main(String[] args) {
        List<FaturaInter> faturaList = new ArrayList<>();
        String path = "C:\\Users\\henrique.r.mendes\\Documentos\\conversor-fatura\\src\\faturaInter.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String faturaCsv = bufferedReader.readLine();
            while (faturaCsv != null) {
                String regex = "(\\d{2})\\s+de\\s+([a-zç]{3,})\\.\\s+(\\d{4})\\s+(.+?)\\s+-\\s+R\\$\\s+([\\d,.]+)";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(faturaCsv);

                if (matcher.find()) {
                    String dia = matcher.group(1);
                    System.out.println("dia: " + dia);

                    String mesExtenso = matcher.group(2).toLowerCase();
                    System.out.println("mesExtenso: " + mesExtenso);

                    String descricao = matcher.group(4).trim();
                    System.out.println("descricao: " + descricao);

                    String valor = matcher.group(5);
                    System.out.println("valor: " + valor);

                    // Convertendo o mês por extenso para numérico
                    String mes = mesParaNumero(mesExtenso);
                    if (mes == null) {
                        System.out.println("Mês inválido encontrado: " + mesExtenso);
                        faturaCsv = bufferedReader.readLine();
                        continue;
                    }

                    String data = dia + "/" + mes;

                    // Adicionando a fatura à lista
                    faturaList.add(new FaturaInter(data, descricao, valor));
                } else {
                    System.out.println("A linha não corresponde ao padrão esperado.");
                    System.out.println("Linha: " + faturaCsv);
                }
                faturaCsv = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
        Date date = new Date();
        String arquivoSaida = "C:\\Users\\henrique.r.mendes\\Documentos\\conversor-fatura\\src\\faturaInterConvertida_" + sdf.format(date) + ".csv";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivoSaida, true))) {
            bufferedWriter.write("tipoCompra;dataCompra;nomeLocal;numParcelas;valorCompra\n");
            for (FaturaInter fatura : faturaList) {
                bufferedWriter.write(fatura.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String mesParaNumero(String mesExtenso) {
        switch (mesExtenso) {
            case "jan": return "01";
            case "fev": return "02";
            case "mar": return "03";
            case "abr": return "04";
            case "mai": return "05";
            case "jun": return "06";
            case "jul": return "07";
            case "ago": return "08";
            case "set": return "09";
            case "out": return "10";
            case "nov": return "11";
            case "dez": return "12";
            default: return null; // Caso o mês não seja reconhecido
        }
    }
}
