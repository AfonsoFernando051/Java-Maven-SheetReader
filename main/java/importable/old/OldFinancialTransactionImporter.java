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

import importable.model.FinancialTransaction;

public class OldFinancialTransactionImporter {

    // Formato de data visto nas suas planilhas (ex: 03/11/2025)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Importação de dados da planilha de Transações Financeiras (FinancialTransaction)
     */
    public static List<FinancialTransaction> importSheetData(String abaPlanilha,
            boolean possuiCabecalho,
            String colunaId,
            String colunaType,
            String colunaCategory,
            String colunaDescription,
            String colunaAmount,
            String colunaCurrency,
            String colunaTransactionDate,
            String colunaPaymentMethod,
            String colunaStatus,
            String colunaSourceAccount,
            String colunaDestinationAccount,
            String colunaReferenceNumber,
            String colunaRelatedEntityId,
            String colunaRelatedEntityType,
            String colunaNotes,
            String colunaCreatedBy,
            String colunaRecordedDate,
            byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<FinancialTransaction> transactions = new ArrayList<>();

            // Mapeia letras das colunas (A=0, B=1, etc.)
            final int POS_ID = ((byte) colunaId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_TYPE = ((byte) colunaType.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CATEGORY = ((byte) colunaCategory.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DESCRIPTION = ((byte) colunaDescription.toUpperCase().toCharArray()[0]) - 65;
            final int POS_AMOUNT = ((byte) colunaAmount.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CURRENCY = ((byte) colunaCurrency.toUpperCase().toCharArray()[0]) - 65;
            final int POS_TRANSACTION_DATE = ((byte) colunaTransactionDate.toUpperCase().toCharArray()[0]) - 65;
            final int POS_PAYMENT_METHOD = ((byte) colunaPaymentMethod.toUpperCase().toCharArray()[0]) - 65;
            final int POS_STATUS = ((byte) colunaStatus.toUpperCase().toCharArray()[0]) - 65;
            final int POS_SOURCE_ACCOUNT = ((byte) colunaSourceAccount.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DESTINATION_ACCOUNT = ((byte) colunaDestinationAccount.toUpperCase().toCharArray()[0]) - 65;
            final int POS_REFERENCE_NUMBER = ((byte) colunaReferenceNumber.toUpperCase().toCharArray()[0]) - 65;
            final int POS_RELATED_ENTITY_ID = ((byte) colunaRelatedEntityId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_RELATED_ENTITY_TYPE = ((byte) colunaRelatedEntityType.toUpperCase().toCharArray()[0]) - 65;
            final int POS_NOTES = ((byte) colunaNotes.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CREATED_BY = ((byte) colunaCreatedBy.toUpperCase().toCharArray()[0]) - 65;
            final int POS_RECORDED_DATE = ((byte) colunaRecordedDate.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                FinancialTransaction transaction = new FinancialTransaction();
                
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    if (columnIndex == POS_ID) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            transaction.setId((long) cell.getNumericCellValue());
                        }
                    } else if (columnIndex == POS_TYPE) {
                        if (cell.getCellType() == CellType.STRING) {
                            transaction.setType(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_CATEGORY) {
                        if (cell.getCellType() == CellType.STRING) {
                            transaction.setCategory(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_DESCRIPTION) {
                        if (cell.getCellType() == CellType.STRING) {
                            transaction.setDescription(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_AMOUNT) {
                        // Tratamento para BigDecimal
                        if (cell.getCellType() == CellType.NUMERIC) {
                            transaction.setAmount(cell.getNumericCellValue());
                        } else if (cell.getCellType() == CellType.STRING) {
                            try {
                                transaction.setAmount(cell.getNumericCellValue());
                            } catch (Exception e) { /* falha silenciosa, como no padrão */ }
                        }
                    } else if (columnIndex == POS_CURRENCY) {
                        if (cell.getCellType() == CellType.STRING) {
                            transaction.setCurrency(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_TRANSACTION_DATE) {
                        // Tratamento para LocalDate
                        if (cell.getCellType() == CellType.STRING) {
                            try {
                                transaction.setTransactionDate(LocalDate.parse(cell.getStringCellValue(), DATE_FORMATTER));
                            } catch (Exception e) { /* falha silenciosa */ }
                        }
                    } else if (columnIndex == POS_PAYMENT_METHOD) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setPaymentMethod(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_STATUS) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setStatus(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_SOURCE_ACCOUNT) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setSourceAccount(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_DESTINATION_ACCOUNT) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setDestinationAccount(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_REFERENCE_NUMBER) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setReferenceNumber(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_RELATED_ENTITY_ID) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            transaction.setRelatedEntityId((long) cell.getNumericCellValue());
                        }
                    } else if (columnIndex == POS_RELATED_ENTITY_TYPE) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setRelatedEntityType(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_NOTES) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setNotes(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_CREATED_BY) {
                         if (cell.getCellType() == CellType.STRING) {
                            transaction.setCreatedBy(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_RECORDED_DATE) {
                        // Tratamento para LocalDate
                        if (cell.getCellType() == CellType.STRING) {
                            try {
                                transaction.setRecordedDate(LocalDate.parse(cell.getStringCellValue(), DATE_FORMATTER));
                            } catch (Exception e) { /* falha silenciosa */ }
                        }
                    }
                }
                // Adiciona à lista apenas se tiver um ID (chave primária)
                if (transaction.getId() != null) {
                    transactions.add(transaction);
                }
            }

            workXssf.close();
            return transactions;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}