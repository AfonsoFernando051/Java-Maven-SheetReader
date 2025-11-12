package importable.run;

import java.util.ArrayList;
import java.util.HashMap;

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.service.ImportService;
import importable.service.PlanilhaImportConfigManager;
import importable.service.factory.ImportServiceFactory;

public class MainDP {

	public static void main(String[] args) {
		readDataWithDesignPatterns();
	}

	private static void readDataWithDesignPatterns() {
		System.out.println("--- Using Factory and Strategy patterns to decouple the import logic ---");
		for (SheetTypeEnum sheet : SheetTypeEnum.values()) {
			
			PlanilhaImportConfigManager planilhasManager = new PlanilhaImportConfigManager();
			ImportService<?> service = ImportServiceFactory.getServiceByType(sheet);
			HashMap<SheetTypeEnum, SheetModel> SheetModel = service.generateSheetModel(sheet);
			planilhasManager.setPlanilhas(SheetModel);
			
			HashMap<SheetTypeEnum, ?> result = service.importBringInsertDataManySheet(planilhasManager, service.getBytesManager(sheet));
			readResult(sheet, result);
		}
	}

	private static void readResult(SheetTypeEnum sheet, HashMap<SheetTypeEnum, ?> result) {
		ArrayList<?> arrayList = (ArrayList<?>) result.get(sheet);
		System.out.println("--- Imported " + arrayList.size() + " records from " + sheet.name() + " ---");
		for (Object object : arrayList) {
			System.out.println(object.toString());
		}
		System.out.println();
	}
}

