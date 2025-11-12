package importable.old;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import importable.model.Order;

public class OldOrderImporter {

    /**
     * Importação de dados da planilha de pedidos
     */
    public static List<Order> importSheetData(String abaPlanilha,
                                                    boolean possuiCabecalho,
                                                    String colunaOrderId,
                                                    String colunaCustomerId,
                                                    String colunaData,
                                                    String colunaValor,
                                                    String colunaStatus,
                                                    byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<Order> orders = new ArrayList<Order>();

            final int POS_ORDER_ID = ((byte) colunaOrderId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CUSTOMER_ID = ((byte) colunaCustomerId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DATA = ((byte) colunaData.toUpperCase().toCharArray()[0]) - 65;
            final int POS_VALOR = ((byte) colunaValor.toUpperCase().toCharArray()[0]) - 65;
            final int POS_STATUS = ((byte) colunaStatus.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                Order order = new Order();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    if (columnIndex == POS_ORDER_ID) {
                        order.setOrderId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_CUSTOMER_ID) {
                        order.setCustomerId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_DATA) {
                        if (cell.getCellType() == CellType.STRING) {
                            order.setOrderDate(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_VALOR) {
                        order.setTotalValue(cell.getNumericCellValue());
                    } else if (columnIndex == POS_STATUS) {
                        order.setStatus(cell.getStringCellValue());
                    }
                }
                if (order.getOrderId() != null) {
                    orders.add(order);
                }
            }

            workXssf.close();
            return orders;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}