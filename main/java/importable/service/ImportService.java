package importable.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.utils.SaveBytesManager;

public interface ImportService<T> {

	public void importBringDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,
			SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback);

	public void importBringInsertDataManySheetByCallback(PlanilhaImportConfigManager planilhaImportConfigManager,
			SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback);

	public HashMap<SheetTypeEnum, SheetModel> generateSheetModel(SheetTypeEnum tipo);

	public SaveBytesManager getBytesManager(SheetTypeEnum tipo);

	public HashMap<SheetTypeEnum, ArrayList<T>> importBringInsertDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,SaveBytesManager bytesController);

}
