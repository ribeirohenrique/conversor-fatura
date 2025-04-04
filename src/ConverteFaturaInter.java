import entities.FaturaInter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConverteFaturaInter {
    public static void main(String[] args) {
        List<FaturaInter> faturaList = new ArrayList<>();
        String path = "src/faturaInter.txt";


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String faturaCsv = bufferedReader.readLine();
            while (faturaCsv != null) {
                // Regex ajustada para capturar opcionalmente as parcelas
                String regex = "(\\d{2})\\s+de\\s+([a-zç]{3,})\\.\\s+(\\d{4})\\s+(.+?)(?:\\s+Parcela\\s*(\\d{2})\\s+de\\s+(\\d{2}))?\\s*-\\s*R\\$\\s*([\\d,.]+)";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(faturaCsv);

                if (matcher.find()) {
                    String dia = matcher.group(1);
                    String mesExtenso = matcher.group(2).toLowerCase();
                    String descricao = matcher.group(4).trim();
                    String parcelaAtual = matcher.group(5);
                    String totalParcelas = matcher.group(6);
                    String valor = matcher.group(7);

                    // Convertendo o mês para número
                    String mes = mesParaNumero(mesExtenso);
                    if (mes == null) {
                        System.out.println("Mês inválido encontrado: " + mesExtenso);
                        faturaCsv = bufferedReader.readLine();
                        continue;
                    }

                    int anoFatura = Integer.parseInt(matcher.group(3));
                    int diaFatura = Integer.parseInt(dia);
                    int mesFatura = Integer.parseInt(mes);

                    // Sempre formata como dd/MM/yyyy
                    String data = String.format("%02d/%02d/%d", diaFatura, mesFatura, anoFatura);

                    // Formatando parcelas
                    String parcelas = (parcelaAtual != null && totalParcelas != null) ? parcelaAtual + "|" + totalParcelas : "-";

                    // Adicionando à lista
                    faturaList.add(new FaturaInter(data, descricao, parcelas, valor));
                } else {
                    System.out.println("A linha não corresponde ao padrão esperado.");
                    System.out.println("Linha: " + faturaCsv);
                }
                faturaCsv = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Criando nome do arquivo de saída
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
        Date date = new Date();
        String arquivoSaida = "src\\faturaInterConvertida_" + sdf.format(date) + ".csv";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivoSaida, true))) {
            bufferedWriter.write("dataCompra;nomeLocal;numParcelas;valorCompra\n");
            for (FaturaInter fatura : faturaList) {
                bufferedWriter.write(fatura.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Conversão de fatura Inter efetuada com sucesso.");
    }

    private static String mesParaNumero(String mesExtenso) {
        return switch (mesExtenso) {
            case "jan" -> "01";
            case "fev" -> "02";
            case "mar" -> "03";
            case "abr" -> "04";
            case "mai" -> "05";
            case "jun" -> "06";
            case "jul" -> "07";
            case "ago" -> "08";
            case "set" -> "09";
            case "out" -> "10";
            case "nov" -> "11";
            case "dez" -> "12";
            default -> null; // Caso o mês não seja reconhecido
        };
    }
}
