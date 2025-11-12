package importable.service;

import java.util.HashMap;
import java.util.Map; // Import the Map interface

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;

/**
 * Manages the configuration for spreadsheet imports.
 *
 * @author Fernando Dias
 */
public class SheetImportConfigManager {

    /**
     * A map of sheet models to be imported, keyed by their type.
     */
    private HashMap<SheetTypeEnum, SheetModel> sheetModels = new HashMap<>();

    /**
     * Returns the single registered sheet model, if there is exactly one.
     *
     * @return the unique {@link SheetModel} present in the map
     * @throws IllegalStateException if there are no sheets or more than one
     * sheet registered.
     */
    public SheetModel getUniqueSheetModel() {
        if (sheetModels == null || sheetModels.isEmpty()) {
            throw new IllegalStateException("No sheet model has been registered.");
        }
        if (sheetModels.size() > 1) {
            throw new IllegalStateException(
                    "More than one sheet model is registered. Use the specific type to retrieve.");
        }
        return sheetModels.values().iterator().next();
    }

    /**
     * Adds a sheet model to the map.
     *
     * @param type        the type of the sheet
     * @param sheetModel updates {@link #sheetModels}.
     */
    public void addSheetModel(SheetTypeEnum type, SheetModel sheetModel) {
        if (this.sheetModels == null) {
            this.sheetModels = new HashMap<>();
        }
        this.sheetModels.put(type, sheetModel);
    }

    /**
     * @return {@link #sheetModels}
     */
    public HashMap<SheetTypeEnum, SheetModel> getSheetModels() {
        return sheetModels;
    }

    /**
     * Gets a specific sheet model by its type.
     *
     * @param type The type of the sheet to retrieve.
     * @return The {@link SheetModel} associated with the type, or null if not found.
     */
    public SheetModel getSheetModelByType(SheetTypeEnum type) {
        return this.sheetModels.get(type);
    }

    /**
     * @param sheetModels updates {@link #sheetModels}.
     */
    public void setSheetModels(HashMap<SheetTypeEnum, SheetModel> sheetModels) {
        this.sheetModels = sheetModels;
    }

}