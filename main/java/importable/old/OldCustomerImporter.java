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

import importable.model.Customer;

public class OldCustomerImporter {

  /**
   * Importação de dados da planilha de clientes
   */
	public static List<Customer> importSheetData(String abaPlanilha,
                                     boolean possuiCabecalho,
                                     String colunaID,
                                     String colunaNome,
                                     String colunaEmail,
                                     String colunaCidade,
                                     String colunaState,
                                     byte[] buf) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
      XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
      int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
      XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
      Iterator<Row> rowIterator = planilha.iterator();
      List<Customer> customers = new ArrayList<Customer>();
      final int POS_ID = ((byte) colunaID.toUpperCase().toCharArray()[0]) - 65;
      final int POS_NOME = ((byte) colunaNome.toUpperCase().toCharArray()[0]) - 65;
      final int POS_EMAIL = ((byte) colunaEmail.toUpperCase().toCharArray()[0]) - 65;
      final int POS_CIDADE = ((byte) colunaCidade.toUpperCase().toCharArray()[0]) - 65;
      final int POS_STATE = ((byte) colunaState.toUpperCase().toCharArray()[0]) - 65;

      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        if (possuiCabecalho && row.getRowNum() < 1) {
          continue;
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        Customer customer = new Customer();
        while (cellIterator.hasNext()) {
          Cell cell = cellIterator.next();
          int columnIndex = cell.getColumnIndex();
          if (columnIndex == POS_ID) {
        	customer.setId((long)cell.getNumericCellValue());
          } else if (columnIndex == POS_NOME) {
            customer.setName(cell.getStringCellValue());
          } else if (columnIndex == POS_EMAIL) {
            customer.setEmail(cell.getStringCellValue());
          } else if (columnIndex == POS_CIDADE) {
            customer.setCity(cell.getStringCellValue());
          } else if (columnIndex == POS_STATE) {
        	  customer.setState(cell.getStringCellValue());
          }
        }
        customers.add(customer);
      }

      workXssf.close();
      return customers;
    } catch (EncryptedDocumentException | IOException e) {
      e.printStackTrace();
    }
	return null;
  }
}
