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

import importable.model.Shipment;

public class OldShipmentImporter {

    /**
     * Importação de dados da planilha de envios (Shipments)
     */
    public static List<Shipment> importSheetData(String abaPlanilha,
                                                       boolean possuiCabecalho,
                                                       String colunaShipmentId,
                                                       String colunaOrderId,
                                                       String colunaTransportadora,
                                                       String colunaRastreio,
                                                       String colunaStatus,
                                                       String colunaDataEstimada,
                                                       byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<Shipment> shipments = new ArrayList<Shipment>();

            final int POS_SHIPMENT_ID = ((byte) colunaShipmentId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_ORDER_ID = ((byte) colunaOrderId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_TRANSPORTADORA = ((byte) colunaTransportadora.toUpperCase().toCharArray()[0]) - 65;
            final int POS_RASTREIO = ((byte) colunaRastreio.toUpperCase().toCharArray()[0]) - 65;
            final int POS_STATUS = ((byte) colunaStatus.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DATA_ESTIMADA = ((byte) colunaDataEstimada.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                Shipment shipment = new Shipment();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    if (columnIndex == POS_SHIPMENT_ID) {
                        shipment.setShipmentId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_ORDER_ID) {
                        shipment.setOrderId((long) cell.getNumericCellValue());
                    } else if (columnIndex == POS_TRANSPORTADORA) {
                        shipment.setCarrier(cell.getStringCellValue());
                    } else if (columnIndex == POS_RASTREIO) {
                        shipment.setTrackingCode(cell.getStringCellValue());
                    } else if (columnIndex == POS_STATUS) {
                        shipment.setStatus(cell.getStringCellValue()); // Leitura direta da String
                    } else if (columnIndex == POS_DATA_ESTIMADA) {
                        if (cell.getCellType() == CellType.STRING) {
                            shipment.setEstimatedDeliveryDate(cell.getStringCellValue());
                        }
                    }
                }
                if (shipment.getShipmentId() != null) {
                    shipments.add(shipment);
                }
            }

            workXssf.close();
            return shipments;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}