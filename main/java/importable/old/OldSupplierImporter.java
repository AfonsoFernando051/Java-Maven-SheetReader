package importable.old;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import importable.model.Supplier;

public class OldSupplierImporter {

    /**
     * Importação de dados da planilha de fornecedores
     */
    public static List<Supplier> importSheetData(String abaPlanilha,
                                                       boolean possuiCabecalho,
                                                       String colunaID,
                                                       String colunaNomeEmpresa,
                                                       String colunaContato,
                                                       String colunaEmail,
                                                       String colunaEndereco,
                                                       byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<Supplier> suppliers = new ArrayList<Supplier>();

            // Calcula a posição das colunas com base na letra (A=0, B=1, etc.)
            final int POS_ID = ((byte) colunaID.toUpperCase().toCharArray()[0]) - 65;
            final int POS_NOME_EMPRESA = ((byte) colunaNomeEmpresa.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CONTATO = ((byte) colunaContato.toUpperCase().toCharArray()[0]) - 65;
            final int POS_EMAIL = ((byte) colunaEmail.toUpperCase().toCharArray()[0]) - 65;
            final int POS_ENDERECO = ((byte) colunaEndereco.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                Supplier supplier = new Supplier();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    // Mapeia colunas para os campos do objeto Supplier
                    if (columnIndex == POS_ID) {
                        supplier.setId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_NOME_EMPRESA) {
                        supplier.setCompanyName(cell.getStringCellValue());
                    } else if (columnIndex == POS_CONTATO) {
                        supplier.setContactPerson(cell.getStringCellValue());
                    } else if (columnIndex == POS_EMAIL) {
                        supplier.setEmail(cell.getStringCellValue());
                    } else if (columnIndex == POS_ENDERECO) {
                        supplier.setAddress(cell.getStringCellValue());
                    }
                    // Outras colunas (CNPJ, Phone) são ignoradas nesta importação
                }
                // Adiciona apenas se tiver um ID, para evitar linhas em branco
                if (supplier.getId() != null) {
                    suppliers.add(supplier);
                }
            }

            workXssf.close();
            return suppliers;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
