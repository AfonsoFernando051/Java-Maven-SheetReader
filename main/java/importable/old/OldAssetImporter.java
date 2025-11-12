package importable.old;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import importable.model.CompanyAsset;

public class OldAssetImporter {

    /**
     * Importação de dados da planilha de Ativos da Empresa (CompanyAsset)
     */
    public static List<CompanyAsset> importSheetData(String abaPlanilha,
                                                           boolean possuiCabecalho,
                                                           String colunaAssetTag,
                                                           String colunaDescricao,
                                                           String colunaCategoria,
                                                           String colunaEmployeeId,
                                                           String colunaDataCompra,
                                                           String colunaStatus,
                                                           byte[] buf) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
            XSSFWorkbook workXssf = new XSSFWorkbook(inputStream);
            int abaIndex = Character.getNumericValue(abaPlanilha.charAt(0));
            XSSFSheet planilha = workXssf.getSheetAt(abaIndex);
            Iterator<Row> rowIterator = planilha.iterator();
            List<CompanyAsset> assets = new ArrayList<CompanyAsset>();

            final int POS_ASSET_TAG = ((byte) colunaAssetTag.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DESCRICAO = ((byte) colunaDescricao.toUpperCase().toCharArray()[0]) - 65;
            final int POS_CATEGORIA = ((byte) colunaCategoria.toUpperCase().toCharArray()[0]) - 65;
            final int POS_EMPLOYEE_ID = ((byte) colunaEmployeeId.toUpperCase().toCharArray()[0]) - 65;
            final int POS_DATA_COMPRA = ((byte) colunaDataCompra.toUpperCase().toCharArray()[0]) - 65;
            final int POS_STATUS = ((byte) colunaStatus.toUpperCase().toCharArray()[0]) - 65;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (possuiCabecalho && row.getRowNum() < 1) { // Pula o cabeçalho
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                CompanyAsset asset = new CompanyAsset();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    if (columnIndex == POS_ASSET_TAG) {
                        asset.setAssetTag(cell.getStringCellValue()); // É uma String
                    } else if (columnIndex == POS_DESCRICAO) {
                        asset.setDescription(cell.getStringCellValue());
                    } else if (columnIndex == POS_CATEGORIA) {
                        asset.setCategory(cell.getStringCellValue());
                    } else if (columnIndex == POS_EMPLOYEE_ID) {
                        // Trata ID numérico (Long)
                        if (cell.getCellType() == CellType.NUMERIC) {
                            asset.setEmployeeId((long) cell.getNumericCellValue());
                        }
                    } else if (columnIndex == POS_DATA_COMPRA) {
                        if (cell.getCellType() == CellType.STRING) {
                            asset.setPurchaseDate(cell.getStringCellValue());
                        }
                    } else if (columnIndex == POS_STATUS) {
                        asset.setStatus(cell.getStringCellValue());
                    }
                }
                if (asset.getAssetTag() != null && !asset.getAssetTag().isEmpty()) {
                    assets.add(asset);
                }
            }

            workXssf.close();
            return assets;
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}