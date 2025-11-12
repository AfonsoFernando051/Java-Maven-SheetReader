package importable.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.mapper.InterfaceSheetMapper;
import importable.service.factory.ModelConfigFactory;
import importable.utils.FileProcessingException;
import importable.utils.SaveBytesManager;

/**
 * @author Fernando Dias
 * @param <T> - The type to be imported
 */
public class ImportSheetService<T> implements ImportService<T> {

    /**
     * Data extracted from the form.
     * (Assuming PlanilhaImportConfigManager will be renamed to SheetImportConfigManager)
     */
    SheetImportConfigManager sheetImportConfigManager;

    /**
     * Class that manages the input stream.
     */
    protected SaveBytesManager bytesController;

    /**
     * The current SheetModel being processed.
     */
    private SheetModel currentSheetModel;

    /**
     * Map of SheetModels, keyed by their type.
     */
    private HashMap<SheetTypeEnum, SheetModel> sheetModels = new HashMap<>();

    public SaveBytesManager getBytesManager(SheetTypeEnum type) {
        InputStream stream = getStream(type);
        SaveBytesManager bytesController = new SaveBytesManager();
        bytesController.setBytes(stream);
        return bytesController;
    }

    public HashMap<SheetTypeEnum, SheetModel> generateSheetModel(SheetTypeEnum type) {
        return (HashMap<SheetTypeEnum, SheetModel>) ModelConfigFactory.generateSheetModel(type);
    }

    protected InputStream getStream(SheetTypeEnum type) {
        return ModelConfigFactory.getResourceAsStream(type);
    }

    /**
     * @param sheetImportConfigManager - Manages form data
     * @param bytesController          - Manages the input stream
     */
    public void configureSheetData(SheetImportConfigManager sheetImportConfigManager,
            SaveBytesManager bytesController) {
        setSheetImportConfigManager(sheetImportConfigManager);
        setBytesController(bytesController);
        configureSheets(sheetImportConfigManager);
    }

    /**
     * @param configuration - configures the sheet(s)
     */
    private void configureSheets(SheetImportConfigManager configuration) {
        if (configuration.getSheetModels().size() == 1) {
            setCurrentSheetModel(configuration.getUniqueSheetModel());
            setSheetModels(configuration.getSheetModels());
        } else {
            setSheetModels(configuration.getSheetModels());
        }
    }

    /**
     * @param cell - the cell
     * @return true if it is invalid (null or blank)
     */
    protected boolean isCellInvalid(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

    /**
     * Imports data from the sheet.
     *
     * @param sheetImportConfigManager - Manages form data
     * @param bytesController          - Manages the input stream
     * @param callback                 - action to refresh a grid
     */
    public void importAndFetchDataFromSheets(SheetImportConfigManager sheetImportConfigManager,
            SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback) {
        try {
            configureSheetData(sheetImportConfigManager, bytesController);
            HashMap<SheetTypeEnum, ArrayList<T>> data = getDataFromSheets();
            bytesController.closeFileData();
            callback.accept(data);
        } catch (EncryptedDocumentException | IOException e) {
            // TODO: Handle exception properly
            e.printStackTrace();
        }
    }

    /**
     * Imports and inserts data from the sheet using a callback.
     *
     * @param sheetImportConfigManager - Manages form data
     * @param bytesController          - Manages the input stream
     * @param callback                 - action to refresh a grid
     */
    public void importAndInsertDataFromSheetsWithCallback(SheetImportConfigManager sheetImportConfigManager,
            SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback) {
        try {
            configureSheetData(sheetImportConfigManager, bytesController);
            HashMap<SheetTypeEnum, ArrayList<T>> data = getDataFromSheets();
            Set<SheetTypeEnum> sheetTypes = data.keySet();
            for (SheetTypeEnum sheetType : sheetTypes) {
                insertSheetData(data.get(sheetType));
            }
            bytesController.closeFileData();
            callback.accept(data);
        } catch (EncryptedDocumentException | IOException e) {
            // TODO: Handle exception properly
            e.printStackTrace();
        }
    }

    /**
     * Imports and inserts data from multiple sheets.
     *
     * @param sheetImportConfigManager - Manages form data
     * @param bytesController          - Manages the input stream
     * @return Map of imported data, or null on failure
     */
    public HashMap<SheetTypeEnum, ArrayList<T>> importAndInsertDataFromSheets(
            SheetImportConfigManager sheetImportConfigManager,
            SaveBytesManager bytesController) {
        try {
            configureSheetData(sheetImportConfigManager, bytesController);
            HashMap<SheetTypeEnum, ArrayList<T>> data = getDataFromSheets();
            Set<SheetTypeEnum> sheetTypes = data.keySet();
            for (SheetTypeEnum sheetType : sheetTypes) {
                insertSheetData(data.get(sheetType));
            }
            bytesController.closeFileData();
            return data;
        } catch (EncryptedDocumentException | IOException e) {
            // TODO: Handle exception properly
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return sheets mapped by type
     * @throws IOException exception from InputStream
     */
    protected HashMap<SheetTypeEnum, ArrayList<T>> getDataFromSheets() throws IOException {
        HashMap<SheetTypeEnum, ArrayList<T>> results = new HashMap<>();
        for (Map.Entry<SheetTypeEnum, SheetModel> entry : getSheetModels().entrySet()) {
            SheetTypeEnum type = entry.getKey();
            setCurrentSheetModel(entry.getValue());

            try (Workbook workbook = generateWorkbook()) {
                InterfaceSheetMapper<T> modelConfig = getModelConfig(type);
                // Assuming GenericPlanilhaProcessor was renamed to GenericSheetProcessor
                GenericSheetProcessor<T> processor = new GenericSheetProcessor<>(modelConfig);
                results.put(type, processor.process(workbook, getCurrentSheetModel()));
            } catch (FileProcessingException e) {
                throw new IOException(e.getMessage());
            }
        }
        return results;
    }

    /**
     * @param type of sheet
     * @return model configuration
     */
    @SuppressWarnings("unchecked")
    InterfaceSheetMapper<T> getModelConfig(SheetTypeEnum type) {
        return (InterfaceSheetMapper<T>) ModelConfigFactory.getModelConfig(type);
    }

    /**
     * @param data - list of elements
     */
    protected void insertSheetData(List<T> data) {
        // Placeholder for implementation
    }

    /**
     * Validates if the event to be inserted already exists.
     * (Assuming "Eventos" is a domain-specific term)
     *
     * @param data - to be validated
     * @return list with validated data
     */
    protected List<T> validateExistingEvents(List<T> data) {
        return data;
    }

    /**
     * @return Workbook based on the file
     * @throws IOException streaming error
     */
    protected Workbook generateWorkbook() throws IOException {
        ByteArrayInputStream inputStream = bytesController.getNewInputWithBytes();
        Workbook workXssf = WorkbookFactory.create(inputStream);
        return workXssf;
    }

    /**
     * @return {@link #sheetImportConfigManager}
     */
    public SheetImportConfigManager getSheetImportConfigManager() {
        return sheetImportConfigManager;
    }

    /**
     * @param sheetImportConfigManager updates
     * {@link #sheetImportConfigManager}.
     */
    public void setSheetImportConfigManager(SheetImportConfigManager sheetImportConfigManager) {
        this.sheetImportConfigManager = sheetImportConfigManager;
    }

    /**
     * @return {@link #bytesController}
     */
    public SaveBytesManager getBytesController() {
        return bytesController;
    }

    /**
     * @param bytesController updates {@link #bytesController}.
     */
    public void setBytesController(SaveBytesManager bytesController) {
        this.bytesController = bytesController;
    }

    /**
     * @return {@link #currentSheetModel}
     */
    public SheetModel getCurrentSheetModel() {
        return currentSheetModel;
    }

    /**
     * @param currentSheetModel updates {@link #currentSheetModel}.
     */
    public void setCurrentSheetModel(SheetModel currentSheetModel) {
        this.currentSheetModel = currentSheetModel;
    }

    /**
     * @return {@link #sheetModels}
     */
    public HashMap<SheetTypeEnum, SheetModel> getSheetModels() {
        return sheetModels;
    }

    /**
     * @param sheetModels updates {@link #sheetModels}.
     */
    public void setSheetModels(HashMap<SheetTypeEnum, SheetModel> sheetModels) {
        this.sheetModels = sheetModels;
    }
}