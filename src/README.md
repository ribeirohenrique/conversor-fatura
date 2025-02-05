# Conversor de Fatura Inter e Santander

## 📌 Sobre o Projeto
Este projeto lê arquivos de fatura no formato de texto (`.txt`), extrai informações relevantes usando expressões regulares e gera arquivos `.csv` formatados com as seguintes colunas:
- **dataCompra**: Data da compra no formato `dd/MM`.
- **nomeLocal**: Nome do estabelecimento onde a compra foi realizada (mascarado no exemplo).
- **numParcelas**: Número da parcela no formato `XX/YY`, ou `-` caso a compra não seja parcelada.
- **valorCompra**: Valor da compra em reais.

Além disso, há suporte para faturas do Santander, que incluem um campo adicional:
- **tipoCompra**: Indica se a compra foi nacional ou internacional.

## 📥 Formato do Arquivo de Entrada
Os arquivos de entrada (`faturaInter.txt` e `faturaSantander.txt`) devem conter as informações das compras com os seguintes formatos:

### 🔹 Fatura Inter
```
DD de MMM. YYYY NOME_DO_ESTABELECIMENTO (Parcela XX de YY) - R$ VALOR
```

#### Exemplos válidos de entrada:
```
02 de out. 2024 COMPRA DE CDS (Parcela 04 de 09) - R$ 28,83
29 de dez. 2024 SUPERMERCADO XYZ (Parcela 02 de 08) - R$ 48,24
01 de jan. 2025 LOJA DE DISCOS - R$ 7,25
02 de jan. 2025 MERCADO LOCAL - R$ 100,00
03 de jan. 2025 LOJA DE ALIMENTOS - R$ 89,00
```

### 🔹 Fatura Santander
```
TIPO_COMPRA DD/MM/YY NOME_DO_ESTABELECIMENTO (Parcela XX de YY) - R$ VALOR
```

#### Exemplos válidos de entrada:
```
3 02/10/24 COMPRA DE CDS (Parcela 04 de 09) - R$ 28,83
29/12/24 SUPERMERCADO XYZ (Parcela 02 de 08) - R$ 48,24
3 01/01/25 LOJA DE DISCOS - R$ 7,25
3 02/01/25 MERCADO LOCAL - R$ 100,00
3 03/01/25 LOJA DE ALIMENTOS - R$ 89,00
```

## 📤 Formato do Arquivo de Saída
Os arquivos gerados terão os nomes no formato:
```
faturaInterConvertida_dd_MM_yyyy__HH_mm_ss.csv
faturaSantanderConvertida_dd_MM_yyyy__HH_mm_ss.csv
```
E o conteúdo será organizado em colunas separadas por `;`.

### 🔹 Fatura Inter
```
dataCompra;nomeLocal;numParcelas;valorCompra
02/10;COMPRA DE CDS;04/09;28,83
29/12;SUPERMERCADO XYZ;02/08;48,24
01/01;LOJA DE DISCOS;-;7,25
02/01;MERCADO LOCAL;-;100,00
03/01;LOJA DE ALIMENTOS;-;89,00
```

### 🔹 Fatura Santander
```
tipoCompra;dataCompra;nomeLocal;valorCompra;numParcelas
NACIONAL;02/10;COMPRA DE CDS;28,83;04/09
INTERNACIONAL;29/12;SUPERMERCADO XYZ;48,24;02/08
NACIONAL;01/01;LOJA DE DISCOS;7,25;-
NACIONAL;02/01;MERCADO LOCAL;100,00;-
NACIONAL;03/01;LOJA DE ALIMENTOS;89,00;-
```

## 🛠 Como Executar
1. Salve os arquivos de fatura em `src/faturaInter.txt` e `src/faturaSantander.txt`.
2. Execute a classe `ConverteFaturaInter.java` para faturas do Inter ou `ConverteFaturaSantander.java` para faturas do Santander.
3. Os arquivos CSV formatados serão gerados na pasta `src`.

---
Agora seu conversor tem suporte para as faturas do Santander e está bem documentado! 🚀

