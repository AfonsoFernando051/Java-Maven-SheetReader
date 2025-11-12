package importable.old;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType; // Importante para checar o tipo da célula
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import importable.model.Employee;

public class OldEmployeeImporter {

    /**
     * Importação de dados da planilha de funcionários
     */
    public static List<Employee> importSheetData(String abaPlanilha,
                                                       boolean possuiCabecalho,
                                                       String colunaID,
                                                       String colunaNome,
                                                       String colunaEmail,
                                                       String colunaDepartamento,
                                                       String colunaCargo,
                                                       String colunaDataContratacao,
                                                       byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<Employee> employees = new ArrayList<Employee>();

            final int POS_ID = ((byte) colunaID.toUpperCase().toCharArray()[0]) - 65;
            final int POS_NOME = ((byte) colunaNome.toUpperCase().toCharArray()[0]) - 65;
            final int POS_EMAIL = ((byte) colunaEmail.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DEPARTAMENTO = ((byte) colunaDepartamento.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CARGO = ((byte) colunaCargo.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DATA_CONTRATACAO = ((byte) colunaDataContratacao.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                Employee employee = new Employee();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    if (columnIndex == POS_ID) {
                        employee.setId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_NOME) {
                        employee.setName(cell.getStringCellValue());
                    } else if (columnIndex == POS_EMAIL) {
                        employee.setEmail(cell.getStringCellValue());
                    } else if (columnIndex == POS_DEPARTAMENTO) {
                        employee.setDepartment(cell.getStringCellValue());
                    } else if (columnIndex == POS_CARGO) {
                        employee.setPosition(cell.getStringCellValue());
                    } else if (columnIndex == POS_DATA_CONTRATACAO) {
                        // Tratamento para data (assumindo que está como string AAAA-MM-DD)
                        if (cell.getCellType() == CellType.STRING) {
                            employee.setHireDate(cell.getStringCellValue());
                        }
                    }
                }
                if (employee.getId() != null) {
                    employees.add(employee);
                }
            }

            workXssf.close();
            return employees;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}