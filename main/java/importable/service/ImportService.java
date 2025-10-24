package importable.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
import importable.utils.SaveBytesManager;

public interface ImportService<T> {
	
	public void importBringDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,
	                                       SaveBytesManager bytesController,
	                                       Consumer<HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>>> callback);
	  
	public void importBringInsertDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,
	                                             SaveBytesManager bytesController,
	                                             Consumer<HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>>> callback);

	public HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> generatePlanilhaModel(TipoPlanilhaImportacaoEnum tipo);
	
	 public SaveBytesManager getBytesManager(TipoPlanilhaImportacaoEnum tipo);
}
