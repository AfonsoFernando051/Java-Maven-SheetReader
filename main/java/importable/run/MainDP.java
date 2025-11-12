package importable.run;

import java.util.ArrayList;
import java.util.HashMap;

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.service.ImportService;
import importable.service.SheetImportConfigManager;
import importable.service.factory.ImportServiceFactory;

public class MainDP {

	public static void main(String[] args) {
		readDataWithDesignPatterns();
	}

	/**
	 * Runs the import process using Factory and Strategy patterns.
	 */
	private static void readDataWithDesignPatterns() {
		System.out.println("--- Using Factory and Strategy patterns to decouple the import logic ---");
		for (SheetTypeEnum sheet : SheetTypeEnum.values()) {
			// Use the translated class and variable names
			SheetImportConfigManager sheetManager = new SheetImportConfigManager();
			ImportService<?> service = ImportServiceFactory.getServiceByType(sheet);
			HashMap<SheetTypeEnum, SheetModel> sheetModel = service.generateSheetModel(sheet);
			
			// Use the translated method name
			sheetManager.setSheetModels(sheetModel); 
			
			// Use the translated method name
			HashMap<SheetTypeEnum, ?> result = service.importAndInsertDataFromSheets(sheetManager, service.getBytesManager(sheet));
			
			ArrayList<?> arrayList = (ArrayList<?>) result.get(sheet);
			System.out.println("--- Imported " + arrayList.size() + " records from " + sheet.name() + " ---");
			for (Object object : arrayList) {
				System.out.println(object.toString());
			}
			System.out.println();
		}
	}
}

