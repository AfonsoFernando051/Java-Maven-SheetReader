package importable.old;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import importable.model.Address;

public class OldAddressImporter {

    /**
     * Importação de dados da planilha de Endereços (Address)
     */
    public static List<Address> importSheetData(String abaPlanilha,
            boolean possuiCabecalho,
            String colunaLogradouro,
            String colunaNumero,
            String colunaComplemento,
            String colunaBairro,
            String colunaCidade,
            String colunaEstado,
            String colunaCep,
            byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<Address> addresses = new ArrayList<>();

            // Mapeia letras das colunas (A=0, B=1, etc.)
            final int POS_LOGRADOURO = ((byte) colunaLogradouro.toUpperCase().toCharArray()[0]) - 65;
            final int POS_NUMERO = ((byte) colunaNumero.toUpperCase().toCharArray()[0]) - 65;
            final int POS_COMPLEMENTO = ((byte) colunaComplemento.toUpperCase().toCharArray()[0]) - 65;
            final int POS_BAIRRO = ((byte) colunaBairro.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CIDADE = ((byte) colunaCidade.toUpperCase().toCharArray()[0]) - 65;
            final int POS_ESTADO = ((byte) colunaEstado.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CEP = ((byte) colunaCep.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                Address address = new Address();
                
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    // Todos os campos são String, mas é uma boa prática checar o tipo
                    if (columnIndex == POS_LOGRADOURO) {
                        if (cell.getCellType() == CellType.STRING) {
                            address.setLogradouro(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_NUMERO) {
                         if (cell.getCellType() == CellType.STRING) {
                            address.setNumero(cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            // Converte número (ex: 123) para String
                            address.setNumero(String.valueOf((long)cell.getNumericCellValue()));
                        }
                    } else if (columnIndex == POS_COMPLEMENTO) {
                        if (cell.getCellType() == CellType.STRING) {
                            address.setComplemento(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_BAIRRO) {
                        if (cell.getCellType() == CellType.STRING) {
                            address.setBairro(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_CIDADE) {
                        if (cell.getCellType() == CellType.STRING) {
                            address.setCidade(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_ESTADO) {
                        if (cell.getCellType() == CellType.STRING) {
                            address.setEstado(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_CEP) {
                        if (cell.getCellType() == CellType.STRING) {
                            address.setCep(cell.getStringCellValue());
                        }
                    }
                }
                // Adiciona à lista apenas se tiver um logradouro (chave primária)
                if (address.getLogradouro() != null && !address.getLogradouro().isEmpty()) {
                    addresses.add(address);
                }
            }

            workXssf.close();
            return addresses;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}