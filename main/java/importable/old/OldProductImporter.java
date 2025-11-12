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

import importable.model.Product;

public class OldProductImporter {

  /**
   * Importação de dados da planilha de produtos
 * @return 
   */
  public static List<Product> importSheetData(String abaPlanilha,
                                     boolean possuiCabecalho,
                                     String colunaID,
                                     String colunaNome,
                                     String colunaPreco,
                                     String colunaCategoria,
                                     byte[] buf) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
      XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
      int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
      XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
      Iterator<Row> rowIterator = planilha.iterator();
      List<Product> products = new ArrayList<Product>();
      final int POS_ID = ((byte) colunaID.toUpperCase().toCharArray()[0]) - 65;
      final int POS_NOME = ((byte) colunaNome.toUpperCase().toCharArray()[0]) - 65;
      final int POS_PRECO = ((byte) colunaPreco.toUpperCase().toCharArray()[0]) - 65;
      final int POS_CATEGORIA = ((byte) colunaCategoria.toUpperCase().toCharArray()[0]) - 65;

      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();
        if (possuiCabecalho && row.getRowNum() < 1) {
          continue;
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        Product product = new Product();
        while (cellIterator.hasNext()) {
          Cell cell = cellIterator.next();
          int columnIndex = cell.getColumnIndex();
          if (columnIndex == POS_ID) {
              product.setId((long)cell.getNumericCellValue());
          } else if (columnIndex == POS_NOME) {
            product.setName(cell.getStringCellValue());
          } else if (columnIndex == POS_PRECO) {
            product.setPrice(cell.getNumericCellValue());
          } else if (columnIndex == POS_CATEGORIA) {
            product.setCategory(cell.getStringCellValue());
          }
        }
        products.add(product);
      }
      workXssf.close();
      return products;
    } catch (EncryptedDocumentException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
