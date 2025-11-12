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

import importable.model.Inventory;

public class OldInventoryImporter {

    /**
     * Importação de dados da planilha de inventário
     */
    public static List<Inventory> importSheetData(String abaPlanilha,
                                                        boolean possuiCabecalho,
                                                        String colunaProductId,
                                                        String colunaWarehouseId,
                                                        String colunaQuantidade,
                                                        String colunaLocalizacao,
                                                        byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<Inventory> inventoryList = new ArrayList<Inventory>();

            final int POS_PRODUCT_ID = ((byte) colunaProductId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_WAREHOUSE_ID = ((byte) colunaWarehouseId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_QUANTIDADE = ((byte) colunaQuantidade.toUpperCase().toCharArray()[0]) - 65;
            final int POS_LOCALIZACAO = ((byte) colunaLocalizacao.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                Inventory inventory = new Inventory();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    if (columnIndex == POS_PRODUCT_ID) {
                        inventory.setProductId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_WAREHOUSE_ID) {
                        inventory.setWarehouseId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_QUANTIDADE) {
                        inventory.setQuantity((int) cell.getNumericCellValue());
                    } else if (columnIndex == POS_LOCALIZACAO) {
                        inventory.setLocationCode(cell.getStringCellValue());
                    }
                }
                if (inventory.getProductId() != null) {
                    inventoryList.add(inventory);
                }
            }

            workXssf.close();
            return inventoryList;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}