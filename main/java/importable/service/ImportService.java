package importable.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.utils.SaveBytesManager;

/**
 * Interface for import services.
 * (Assumes PlanilhaImportConfigManager was renamed to SheetImportConfigManager)
 *
 * @param <T> The type of object to be imported.
 */
public interface ImportService<T> {

    /**
     * Imports data from multiple sheets and returns it via a callback.
     *
     * @param sheetImportConfigManager The configuration manager.
     * @param bytesController          The byte manager for the file.
     * @param callback                 The consumer to accept the resulting map of data.
     */
    public void importAndFetchDataFromSheets(SheetImportConfigManager sheetImportConfigManager,
            SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback);

    /**
     * Imports, inserts data from multiple sheets, and returns the data via a callback.
     *
     * @param sheetImportConfigManager The configuration manager.
     * @param bytesController          The byte manager for the file.
     * @param callback                 The consumer to accept the resulting map of data after insertion.
     */
    public void importAndInsertDataFromSheetsWithCallback(SheetImportConfigManager sheetImportConfigManager,
            SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback);

    /**
     * Generates the sheet model configuration for a given type.
     *
     * @param type The sheet type.
     * @return A map containing the SheetModel configuration.
     */
    public HashMap<SheetTypeEnum, SheetModel> generateSheetModel(SheetTypeEnum type);

    /**
     * Gets a byte manager for a specific sheet type (likely a template).
     *
     * @param type The sheet type.
     * @return The SaveBytesManager.
     */
    public SaveBytesManager getBytesManager(SheetTypeEnum type);

    /**
     * Imports and inserts data from multiple sheets, returning the processed data.
     *
     * @param sheetImportConfigManager The configuration manager.
     * @param bytesController          The byte manager for the file.
     * @return A map of the imported data.
     */
    public HashMap<SheetTypeEnum, ArrayList<T>> importAndInsertDataFromSheets(
            SheetImportConfigManager sheetImportConfigManager, SaveBytesManager bytesController);

}