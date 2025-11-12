package importable.old;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import importable.model.Project;

public class OldProjectImporter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Importação de dados da planilha de Projetos (Project)
     */
    public static List<Project> importSheetData(String abaPlanilha,
            boolean possuiCabecalho,
            String colunaId,
            String colunaName,
            String colunaDescription,
            String colunaProjectManager,
            String colunaStatus,
            String colunaPriority,
            String colunaStartDate,
            String colunaExpectedEndDate,
            String colunaActualEndDate,
            String colunaAllocatedBudget,
            String colunaActualSpent,
            String colunaClient,
            String colunaCategory,
            String colunaCompletionPercentage,
            String colunaRiskLevel,
            byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<Project> projects = new ArrayList<>();

            // Mapeia letras das colunas (A=0, B=1, etc.)
            final int POS_ID = ((byte) colunaId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_NAME = ((byte) colunaName.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DESCRIPTION = ((byte) colunaDescription.toUpperCase().toCharArray()[0]) - 65;
            final int POS_PROJECT_MANAGER = ((byte) colunaProjectManager.toUpperCase().toCharArray()[0]) - 65;
            final int POS_STATUS = ((byte) colunaStatus.toUpperCase().toCharArray()[0]) - 65;
            final int POS_PRIORITY = ((byte) colunaPriority.toUpperCase().toCharArray()[0]) - 65;
            final int POS_START_DATE = ((byte) colunaStartDate.toUpperCase().toCharArray()[0]) - 65;
            final int POS_EXPECTED_END_DATE = ((byte) colunaExpectedEndDate.toUpperCase().toCharArray()[0]) - 65;
            final int POS_ACTUAL_END_DATE = ((byte) colunaActualEndDate.toUpperCase().toCharArray()[0]) - 65;
            final int POS_ALLOCATED_BUDGET = ((byte) colunaAllocatedBudget.toUpperCase().toCharArray()[0]) - 65;
            final int POS_ACTUAL_SPENT = ((byte) colunaActualSpent.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CLIENT = ((byte) colunaClient.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CATEGORY = ((byte) colunaCategory.toUpperCase().toCharArray()[0]) - 65;
            final int POS_COMPLETION_PERCENTAGE = ((byte) colunaCompletionPercentage.toUpperCase().toCharArray()[0]) - 65;
            final int POS_RISK_LEVEL = ((byte) colunaRiskLevel.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                Project project = new Project();
                
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    if (columnIndex == POS_ID) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            project.setId((long) cell.getNumericCellValue());
                        }
                    } else if (columnIndex == POS_NAME) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setName(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_DESCRIPTION) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setDescription(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_PROJECT_MANAGER) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setProjectManager(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_STATUS) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setStatus(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_PRIORITY) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setPriority(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_START_DATE) {
                        if (cell.getCellType() == CellType.STRING) {
                            try {
                                project.setStartDate(LocalDate.parse(cell.getStringCellValue(), DATE_FORMATTER));
                            } catch (Exception e) { /* falha silenciosa */ }
                        }
                    } else if (columnIndex == POS_EXPECTED_END_DATE) {
                        if (cell.getCellType() == CellType.STRING) {
                             try {
                                project.setExpectedEndDate(LocalDate.parse(cell.getStringCellValue(), DATE_FORMATTER));
                            } catch (Exception e) { /* falha silenciosa */ }
                        }
                    } else if (columnIndex == POS_ACTUAL_END_DATE) {
                         if (cell.getCellType() == CellType.STRING) {
                             try {
                                project.setActualEndDate(LocalDate.parse(cell.getStringCellValue(), DATE_FORMATTER));
                            } catch (Exception e) { /* falha silenciosa */ }
                        }
                    } else if (columnIndex == POS_ALLOCATED_BUDGET) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            project.setAllocatedBudget(cell.getNumericCellValue());
                        } else if (cell.getCellType() == CellType.STRING) {
                             try {
                                project.setAllocatedBudget(cell.getNumericCellValue());
                            } catch (Exception e) { /* falha silenciosa */ }
                        }
                    } else if (columnIndex == POS_ACTUAL_SPENT) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            project.setActualSpent(cell.getNumericCellValue());
                        } else if (cell.getCellType() == CellType.STRING) {
                             try {
                                project.setActualSpent(cell.getNumericCellValue());
                            } catch (Exception e) { /* falha silenciosa */ }
                        }
                    } else if (columnIndex == POS_CLIENT) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setClient(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_CATEGORY) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setCategory(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_COMPLETION_PERCENTAGE) {
                        // Tratamento para Integer
                        if (cell.getCellType() == CellType.NUMERIC) {
                            project.setCompletionPercentage((int) cell.getNumericCellValue());
                        }
                    } else if (columnIndex == POS_RISK_LEVEL) {
                        if (cell.getCellType() == CellType.STRING) {
                            project.setRiskLevel(cell.getStringCellValue());
                        }
                    }
                }
                // Adiciona à lista apenas se tiver um ID (chave primária)
                if (project.getId() != null) {
                    projects.add(project);
                }
            }

            workXssf.close();
            return projects;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}