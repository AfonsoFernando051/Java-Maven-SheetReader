package importable.service.factory;

import java.io.InputStream;
import java.util.HashMap;

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
import importable.mapper.CustomerImportationMapper;
import importable.mapper.InterfacePlanilhaMapper;
import importable.mapper.ProductImportationMapper;
import importable.model.customer.Customer;
import importable.model.product.Product;
import importable.run.MainDP;
import importable.translator.Translator;

public class ModelConfigFactory {

	/**
	 * @param tipo de planilha
	 * @return configuração de modelo
	 */
	public static InterfacePlanilhaMapper<?> getModelConfig(TipoPlanilhaImportacaoEnum tipo) {
		switch (tipo) {
		case PRODUCTS:
			return (InterfacePlanilhaMapper<?>) new ProductImportationMapper(Product.class);
		case CUSTOMERS:
			return (InterfacePlanilhaMapper<?>) new CustomerImportationMapper(Customer.class);
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}

	/**
	 * @param tipo de planilha
	 * @return configuração de modelo
	 */
	public static InputStream getResourceAsStream(TipoPlanilhaImportacaoEnum tipo) {
		switch (tipo) {
		case PRODUCTS:
			return MainDP.class.getClassLoader().getResourceAsStream("products.xlsx");
		case CUSTOMERS:
			return MainDP.class.getClassLoader().getResourceAsStream("customers.xlsx");
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}

	/**
	 * @param tipo de planilha
	 * @return configuração de modelo
	 */
	public static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> generatePlanilhaModel(TipoPlanilhaImportacaoEnum tipo) {
		switch (tipo) {
		case PRODUCTS:
			return mapProducts();
		case CUSTOMERS:
			return mapCustomers();
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapCustomers() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.CUSTOMERS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.CUSTOMERS);

		planilha.addColunaEValor(Translator.ID, "A");
		planilha.addColunaEValor(Translator.NAME, "B");
		planilha.addColunaEValor(Translator.CPF, "C");
		planilha.addColunaEValor(Translator.EMAIL, "D");
		planilha.addColunaEValor(Translator.BIRTH_DATE, "E");
		planilha.addColunaEValor(Translator.CITY, "F");
		planilha.addColunaEValor(Translator.STATE, "G");

		planilhaMap.put(TipoPlanilhaImportacaoEnum.CUSTOMERS, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapProducts() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.PRODUCTS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.PRODUCTS);
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.PRODUCTS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.PRODUCTS);

		planilha.addColunaEValor(Translator.ID, "A");
		planilha.addColunaEValor(Translator.NAME, "B");
		planilha.addColunaEValor(Translator.CATEGORY, "C");
		planilha.addColunaEValor(Translator.PRICE, "D");
		planilha.addColunaEValor(Translator.QUANTITY, "E");
		planilha.addColunaEValor(Translator.REGISTRATION_DATE, "F");

		planilhaMap.put(TipoPlanilhaImportacaoEnum.PRODUCTS, planilha);
		return planilhaMap;
	}

}
