import entities.Fatura;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<Fatura> faturaList = new ArrayList<>();
        String path = "C:\\Users\\henrique\\Documentos\\conversor-fatura\\src\\fatura.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String faturaCsv = bufferedReader.readLine();
            while (faturaCsv != null) {
                //String regex = "(\\d*)\\s*(\\d{2}/\\d{2})\\s+(.+?)\\s+(\\d{2}/\\d{2})\\s+(\\d+,\\d{2})";
                String regex = "(\\d*)\\s*(\\d{2}/\\d{2})\\s+(.+?)(?:\\s+(\\d{2}/\\d{2}))?\\s+(\\d+,\\d{2})";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(faturaCsv);

                if (matcher.find()) {
                    String tipo = matcher.group(1).isEmpty() ? "0" : matcher.group(1);
                    String data = matcher.group(2);
                    String nome = matcher.group(3).trim();
                    String parcelas = (matcher.group(4) == null || matcher.group(4).isEmpty()) ? "N/A" : matcher.group(4).replace("/", "|");
                    String valor = matcher.group(5);
                    faturaList.add(new Fatura(tipo, data, nome, parcelas, valor));
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
        String arquivoSaida = "C:\\Users\\henrique\\Documentos\\conversor-fatura\\src\\faturaConvertida_" + sdf.format(date) + ".csv";
        //mesmo procedimento de leitura de arquivos, o parâmetro true no FileWriter significa que vai definir como append
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivoSaida, true))) {
            bufferedWriter.write("tipoCompra;dataCompra;nomeLocal;numParcelas;valorCompra\n");
            for (Fatura fatura : faturaList) {
                bufferedWriter.write(fatura.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
