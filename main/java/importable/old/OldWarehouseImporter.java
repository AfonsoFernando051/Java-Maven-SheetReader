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

import importable.model.Warehouse;

public class OldWarehouseImporter {

	/**
	 * Importação de dados da planilha de Armazéns (Warehouse)
	 */
	public static List<Warehouse> importSheetData(String abaPlanilha, boolean possuiCabecalho,
			String colunaWarehouseId, String colunaNome, String colunaCidade, String colunaCapacidade, byte[] buf) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
			XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
			int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
			XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
			Iterator<Row> rowIterator = planilha.iterator();
			List<Warehouse> warehouses = new ArrayList<Warehouse>();

			final int POS_WAREHOUSE_ID = ((byte) colunaWarehouseId.toUpperCase().toCharArray()[0]) - 65;
			final int POS_NOME = ((byte) colunaNome.toUpperCase().toCharArray()[0]) - 65;
			final int POS_CIDADE = ((byte) colunaCidade.toUpperCase().toCharArray()[0]) - 65;
			final int POS_CAPACIDADE = ((byte) colunaCapacidade.toUpperCase().toCharArray()[0]) - 65;

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				Warehouse warehouse = new Warehouse();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();

					if (columnIndex == POS_WAREHOUSE_ID) {
						warehouse.setWarehouseId((long) cell.getNumericCellValue());
					} else if (columnIndex == POS_NOME) {
						warehouse.setName(cell.getStringCellValue());
					} else if (columnIndex == POS_CIDADE) {
						warehouse.setCity(cell.getStringCellValue());
					} else if (columnIndex == POS_CAPACIDADE) {
						warehouse.setCapacity((int) cell.getNumericCellValue());
					}
				}
				if (warehouse.getWarehouseId() != null) {
					warehouses.add(warehouse);
				}
			}

			workXssf.close();
			return warehouses;
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}