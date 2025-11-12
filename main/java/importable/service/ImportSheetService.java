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
import importable.mapper.InterfacePlanilhaMapper;
import importable.service.factory.ModelConfigFactory;
import importable.utils.ProcessamentoArquivoException;
import importable.utils.SaveBytesManager;

/**
 * @author Fernando Dias
 * @param <T> - Tipo a ser importado
 */
public  class ImportSheetService<T> implements ImportService<T> {

	/**
	 * Dados extraidos do formulario
	 */
	PlanilhaImportConfigManager planilhaImportConfigManager;

	/**
	 * Classe que gerencia input stream
	 */
	protected SaveBytesManager bytesController;

	/**
	 * Modelo de Importação
	 */
	private SheetModel planilhaAtual;

	/**
	 * Modelo de Importação
	 */
	private HashMap<SheetTypeEnum, SheetModel> planilhas = new HashMap<SheetTypeEnum, SheetModel>();

	public SaveBytesManager getBytesManager(SheetTypeEnum tipo) {
		InputStream stream = getStream(tipo);
		SaveBytesManager bytesController = new SaveBytesManager();
		bytesController.setBytes(stream);
		return bytesController;
	}
	
	public HashMap<SheetTypeEnum, SheetModel> generateSheetModel(SheetTypeEnum tipo){
		return ModelConfigFactory.generateSheetModel(tipo);
	}

	protected InputStream getStream(SheetTypeEnum tipo) {
		return ModelConfigFactory.getResourceAsStream(tipo);
	}

	/**
	 * @param planilhaImportConfigManager - Gerencia dados do form
	 * @param bytesController             - Gerencia input stream
	 */
	public void configurarDadosPlanilha(PlanilhaImportConfigManager planilhaImportConfigManager,
			SaveBytesManager bytesController) {
		setPlanilhaImportConfigManager(planilhaImportConfigManager);
		setBytesController(bytesController);
		configPlanilha(planilhaImportConfigManager);
	}

	/**
	 * @param configuracao - configura planilha/planilhas
	 */
	private void configPlanilha(PlanilhaImportConfigManager configuracao) {
		if (configuracao.getPlanilhas().size() == 1) {
			setPlanilhaAtual(configuracao.getUniquePlanilha());
			setPlanilhas(configuracao.getPlanilhas());
		} else {
			setPlanilhas(configuracao.getPlanilhas());
		}

	}

	/**
	 * @param cell - celula
	 * @return true se for invalida(nula ou vazia)
	 */
	protected boolean isCelulaInvalida(Cell cell) {
		return cell == null || cell.getCellType() == CellType.BLANK;
	}

	/**
	 * Importação de dados da planilha
	 *
	 * @param planilhaImportConfigManager - Gerencia dados do form
	 * @param bytesController             - Gerencia input stream
	 * @param callback                    - ato de refresh em grid
	 */
	public void importBringDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,
			SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback) {
		try {
			configurarDadosPlanilha(planilhaImportConfigManager, bytesController);
			HashMap<SheetTypeEnum, ArrayList<T>> dados = getDadosByPlanilhas();
			bytesController.closeFileData();
			callback.accept(dados);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Importação de dados da planilha
	 *
	 * @param planilhaImportConfigManager - Gerencia dados do form
	 * @param bytesController             - Gerencia input stream
	 * @param callback                    - ato de refresh em grid
	 */
	public void importBringInsertDataManySheetByCallback(PlanilhaImportConfigManager planilhaImportConfigManager,
			SaveBytesManager bytesController, Consumer<HashMap<SheetTypeEnum, ArrayList<T>>> callback) {
		try {
			configurarDadosPlanilha(planilhaImportConfigManager, bytesController);
			HashMap<SheetTypeEnum, ArrayList<T>> dados = getDadosByPlanilhas();
			Set<SheetTypeEnum> keySet = dados.keySet();
			for (SheetTypeEnum SheetTypeEnum : keySet) {
				insertDadosPlanilha(dados.get(SheetTypeEnum));
			}
			bytesController.closeFileData();
			callback.accept(dados);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Importação de dados da planilha
	 *
	 * @param planilhaImportConfigManager - Gerencia dados do form
	 * @param bytesController             - Gerencia input stream
	 * @param callback                    - ato de refresh em grid
	 * @return 
	 */
	public HashMap<SheetTypeEnum, ArrayList<T>> importBringInsertDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,
			SaveBytesManager bytesController) {
		try {
			configurarDadosPlanilha(planilhaImportConfigManager, bytesController);
			HashMap<SheetTypeEnum, ArrayList<T>> dados = getDadosByPlanilhas();
			Set<SheetTypeEnum> keySet = dados.keySet();
			for (SheetTypeEnum SheetTypeEnum : keySet) {
				insertDadosPlanilha(dados.get(SheetTypeEnum));
			}
			bytesController.closeFileData();
			return dados;
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * @throws IOException exceção de InputStream
	 * @return planilhas mapeadas por tipos
	 */
	protected HashMap<SheetTypeEnum, ArrayList<T>> getDadosByPlanilhas() throws IOException {
		HashMap<SheetTypeEnum, ArrayList<T>> resultados = new HashMap<>();
		for (Map.Entry<SheetTypeEnum, SheetModel> entry : getPlanilhas().entrySet()) {
			SheetTypeEnum tipo = entry.getKey();
			setPlanilhaAtual(entry.getValue());

			try (Workbook workbook = generateWorkbook()) {
				InterfacePlanilhaMapper<T> modelConfig = getModelConfig(tipo);
				GenericPlanilhaProcessor<T> processor = new GenericPlanilhaProcessor<>(modelConfig);
				resultados.put(tipo, processor.processar(workbook, getPlanilhaAtual()));
			} catch (ProcessamentoArquivoException e) {
				throw new IOException(e.getMessage());
			}
		}

		return resultados;
	}

	/**
	 * @param tipo de planilha
	 * @return configuração de modelo
	 */
	@SuppressWarnings("unchecked")
	InterfacePlanilhaMapper<T> getModelConfig(SheetTypeEnum tipo) {
		return (InterfacePlanilhaMapper<T>) ModelConfigFactory.getModelConfig(tipo);
	}

	/**
	 * @param dados - lista de elementos
	 */
	protected void insertDadosPlanilha(List<T> dados) {

	};

	/**
	 * Valida se o evento a ser inserido já existe
	 *
	 * @param dados - a serem validados
	 * @return lista com dados validados
	 */
	protected List<T> validateEventosExistentes(List<T> dados) {
		return dados;
	}

	/**
	 * @return Workbook de acordo com arquivo
	 * @throws IOException erro de streaming
	 */
	protected Workbook generateWorkbook() throws IOException {
		ByteArrayInputStream inputStream = bytesController.getNewInputWithBytes();
		Workbook workXssf = WorkbookFactory.create(inputStream);
		return workXssf;
	}

	/**
	 * @return {@link #planilhaImportConfigManager}
	 */
	public PlanilhaImportConfigManager getPlanilhaImportConfigManager() {
		return planilhaImportConfigManager;
	}

	/**
	 * @param planilhaImportConfigManager atualiza
	 *                                    {@link #planilhaImportConfigManager}.
	 */
	public void setPlanilhaImportConfigManager(PlanilhaImportConfigManager planilhaImportConfigManager) {
		this.planilhaImportConfigManager = planilhaImportConfigManager;
	}

	/**
	 * @return {@link #bytesController}
	 */
	public SaveBytesManager getBytesController() {
		return bytesController;
	}

	/**
	 * @param bytesController atualiza {@link #bytesController}.
	 */
	public void setBytesController(SaveBytesManager bytesController) {
		this.bytesController = bytesController;
	}

	/**
	 * @return {@link #planilhaAtual}
	 */
	public SheetModel getPlanilhaAtual() {
		return planilhaAtual;
	}

	/**
	 * @param planilhaAtual atualiza {@link #planilhaAtual}.
	 */
	public void setPlanilhaAtual(SheetModel planilhaAtual) {
		this.planilhaAtual = planilhaAtual;
	}

	/**
	 * @return {@link #planilhas}
	 */
	public HashMap<SheetTypeEnum, SheetModel> getPlanilhas() {
		return planilhas;
	}

	/**
	 * @param planilhas atualiza {@link #planilhas}.
	 */
	public void setPlanilhas(HashMap<SheetTypeEnum, SheetModel> planilhas) {
		this.planilhas = planilhas;
	}

}
