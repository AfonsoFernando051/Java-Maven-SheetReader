package importable.run;

import java.util.ArrayList;
import java.util.HashMap;

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
import importable.service.ImportService;
import importable.service.PlanilhaImportConfigManager;
import importable.service.factory.ImportServiceFactory;

public class MainDP {

	public static void main(String[] args) {
		readDataWithDesignPatterns();
	}

	private static void readDataWithDesignPatterns() {
		System.out.println("--- Using Factory and Strategy patterns to decouple the import logic ---");
		for (TipoPlanilhaImportacaoEnum sheet : TipoPlanilhaImportacaoEnum.values()) {
			PlanilhaImportConfigManager planilhasManager = new PlanilhaImportConfigManager();
			ImportService<?> service = ImportServiceFactory.getServiceByType(sheet);
			HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaModel = service.generatePlanilhaModel(sheet);
			planilhasManager.setPlanilhas(planilhaModel);
			service.importBringInsertDataManySheet(planilhasManager, service.getBytesManager(sheet), t -> {
				ArrayList<?> arrayList = t.get(sheet);
				System.out.println("--- Imported " + arrayList.size() + " records from " + sheet.name() + " ---");
				for (Object object : arrayList) {
					System.out.println(object.toString());
				}
				System.out.println();
			});
		}
	}
}