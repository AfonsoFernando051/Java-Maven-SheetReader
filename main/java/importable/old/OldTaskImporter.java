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

import importable.model.Task;

public class OldTaskImporter {

	/**
	 * Importação de dados da planilha de Tarefas (Tasks)
	 */
	public static List<Task> importSheetData(String abaPlanilha, boolean possuiCabecalho, String colunaTaskId,
			String colunaProjectId, String colunaDescricao, String colunaAssigneeId, String colunaPrioridade,
			String colunaDataVencimento, byte[] buf) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
			XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
			int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
			XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
			Iterator<Row> rowIterator = planilha.iterator();
			List<Task> tasks = new ArrayList<Task>();

			final int POS_TASK_ID = ((byte) colunaTaskId.toUpperCase().toCharArray()[0]) - 65;
			final int POS_PROJECT_ID = ((byte) colunaProjectId.toUpperCase().toCharArray()[0]) - 65;
			final int POS_DESCRICAO = ((byte) colunaDescricao.toUpperCase().toCharArray()[0]) - 65;
			final int POS_ASSIGNEE_ID = ((byte) colunaAssigneeId.toUpperCase().toCharArray()[0]) - 65;
			final int POS_PRIORIDADE = ((byte) colunaPrioridade.toUpperCase().toCharArray()[0]) - 65;
			final int POS_DATA_VENCIMENTO = ((byte) colunaDataVencimento.toUpperCase().toCharArray()[0]) - 65;

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				Task task = new Task();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();

					if (columnIndex == POS_TASK_ID) {
						task.setTaskId((long) cell.getNumericCellValue());
					} else if (columnIndex == POS_PROJECT_ID) {
						task.setProjectId((long) cell.getNumericCellValue());
					} else if (columnIndex == POS_DESCRICAO) {
						task.setDescription(cell.getStringCellValue());
					} else if (columnIndex == POS_ASSIGNEE_ID) {
						if (cell.getCellType() == CellType.NUMERIC) {
							task.setAssigneeId((long) cell.getNumericCellValue());
						}
					} else if (columnIndex == POS_PRIORIDADE) {
						task.setPriority(cell.getStringCellValue()); // String simples
					} else if (columnIndex == POS_DATA_VENCIMENTO) {
						if (cell.getCellType() == CellType.STRING) {
							task.setDueDate(cell.getStringCellValue());
						}
					}
				}
				if (task.getTaskId() != null) {
					tasks.add(task);
				}
			}

			workXssf.close();
			return tasks;
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}