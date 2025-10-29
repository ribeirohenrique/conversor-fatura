import entities.FaturaSantander;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;

public class ConverteFaturaSantander {

    public static void main(String[] args) {
        List<FaturaSantander> faturaList = new ArrayList<>();
        String path = "C:\\Users\\henrique.r.mendes\\Documentos\\conversor-fatura\\src\\faturaSantander.txt";

        // Obtém o ano e mês atuais
        Calendar cal = Calendar.getInstance();
        int anoAtual = cal.get(Calendar.YEAR);
        int mesAtual = cal.get(Calendar.MONTH) + 1; // Mês é de 0 a 11

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String faturaCsv = bufferedReader.readLine();
            while (faturaCsv != null) {
                String regex = "(\\d*)\\s*(\\d{2}/\\d{2})\\s+(.+?)(?:\\s+(\\d{2}/\\d{2}))?\\s+(\\d+,\\d{2})";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(faturaCsv);

                if (matcher.find()) {
                    String tipo = matcher.group(1).isEmpty() ? "0" : matcher.group(1);
                    String data = matcher.group(2);
                    String nome = matcher.group(3).trim();
                    String parcelas = (matcher.group(4) == null || matcher.group(4).isEmpty()) ? "N/A" : matcher.group(4).replace("/", "|");
                    String valor = matcher.group(5);

                    // Extrai o mês da data da transação
                    int mesTransacao = Integer.parseInt(data.substring(3, 5));

                    // Adiciona o ano à data da transação
                    String dataCompleta = data + "/" + (mesTransacao > mesAtual ? anoAtual - 1 : anoAtual);

                    faturaList.add(new FaturaSantander(tipo, dataCompleta, nome, parcelas, valor));
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
        String arquivoSaida = "C:\\Users\\henrique.r.mendes\\Documentos\\conversor-fatura\\src\\faturaSantanderConvertida_" + sdf.format(date) + ".csv";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivoSaida, true))) {
            bufferedWriter.write("tipoCompra;dataCompra;nomeLocal;numParcelas;valorCompra\n");
            for (FaturaSantander fatura : faturaList) {
                bufferedWriter.write(fatura.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}