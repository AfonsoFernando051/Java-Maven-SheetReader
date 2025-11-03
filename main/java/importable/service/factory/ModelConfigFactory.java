package importable.service.factory;

import java.io.InputStream;
import java.util.HashMap;

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
// Imports dos Mappers
import importable.mapper.AssetImportationMapper;
import importable.mapper.CustomerImportationMapper;
import importable.mapper.EmployeeImportationMapper;
import importable.mapper.InterfacePlanilhaMapper;
import importable.mapper.InventoryImportationMapper;
import importable.mapper.OrderImportationMapper;
import importable.mapper.ProductImportationMapper;
import importable.mapper.ShipmentImportationMapper;
import importable.mapper.SupplierImportationMapper;
import importable.mapper.TaskImportationMapper;
import importable.mapper.WarehouseImportationMapper;
import importable.model.CompanyAsset;
import importable.model.Customer;
import importable.model.Employee;
import importable.model.Inventory;
import importable.model.Order;
import importable.model.Product;
import importable.model.Shipment;
import importable.model.Supplier;
import importable.model.Task;
import importable.model.Warehouse;
// Outros
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
		case SUPPLIERS:
			return (InterfacePlanilhaMapper<?>) new SupplierImportationMapper(Supplier.class);
		case EMPLOYEES:
			return (InterfacePlanilhaMapper<?>) new EmployeeImportationMapper(Employee.class);
		case ORDERS:
			return (InterfacePlanilhaMapper<?>) new OrderImportationMapper(Order.class);
		case INVENTORY:
			return (InterfacePlanilhaMapper<?>) new InventoryImportationMapper(Inventory.class);
		case SHIPMENTS:
			return (InterfacePlanilhaMapper<?>) new ShipmentImportationMapper(Shipment.class);
		case ASSETS:
			return (InterfacePlanilhaMapper<?>) new AssetImportationMapper(CompanyAsset.class);
		case TASKS:
			return (InterfacePlanilhaMapper<?>) new TaskImportationMapper(Task.class);
		case WAREHOUSES:
			return (InterfacePlanilhaMapper<?>) new WarehouseImportationMapper(Warehouse.class);
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
		case SUPPLIERS:
			return MainDP.class.getClassLoader().getResourceAsStream("suppliers.xlsx");
		case EMPLOYEES:
			return MainDP.class.getClassLoader().getResourceAsStream("employees.xlsx");
		case ORDERS:
			return MainDP.class.getClassLoader().getResourceAsStream("orders.xlsx");
		case INVENTORY:
			return MainDP.class.getClassLoader().getResourceAsStream("inventory.xlsx");
		case SHIPMENTS:
			return MainDP.class.getClassLoader().getResourceAsStream("shipments.xlsx");
		case ASSETS:
			return MainDP.class.getClassLoader().getResourceAsStream("assets.xlsx");
		case TASKS:
			return MainDP.class.getClassLoader().getResourceAsStream("tasks.xlsx");
		case WAREHOUSES:
			return MainDP.class.getClassLoader().getResourceAsStream("warehouses.xlsx");
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
		case SUPPLIERS:
			return mapSuppliers();
		case EMPLOYEES:
			return mapEmployees();
		case ORDERS:
			return mapOrders();
		case INVENTORY:
			return mapInventory();
		case SHIPMENTS:
			return mapShipments();
		case ASSETS:
			return mapAssets();
		case TASKS:
			return mapTasks();
		case WAREHOUSES:
			return mapWarehouses();
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}

	// --- MÉTODOS DE MAPEAMENTO PRIVADOS ---

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapCustomers() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.CUSTOMERS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.CUSTOMERS);

		// Mapeamento baseado no OldCustomerImporter: "A", "B", "D", "F", "G"
		planilha.addColunaEValor(Translator.ID, "A");
		planilha.addColunaEValor(Translator.NAME, "B");
		// planilha.addColunaEValor(Translator.CPF, "C"); // Não estava no seu Old
		planilha.addColunaEValor(Translator.EMAIL, "D");
		// planilha.addColunaEValor(Translator.BIRTH_DATE, "E"); // Não estava no seu Old
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

		// Mapeamento baseado no OldProductImporter: "A", "B", "D", "C"
		planilha.addColunaEValor(Translator.ID, "A");
		planilha.addColunaEValor(Translator.NAME, "B");
		planilha.addColunaEValor(Translator.PRICE, "D"); // Preço era D
		planilha.addColunaEValor(Translator.CATEGORY, "C"); // Categoria era C
		// planilha.addColunaEValor(Translator.QUANTITY, "E"); // Não estava no seu Old
		// planilha.addColunaEValor(Translator.REGISTRATION_DATE, "F"); // Não estava no seu Old

		planilhaMap.put(TipoPlanilhaImportacaoEnum.PRODUCTS, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapSuppliers() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.SUPPLIERS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.SUPPLIERS);

		// Mapeamento baseado no OldSupplierImporter: "A", "B", "D", "E", "G"
		planilha.addColunaEValor(Translator.ID, "A");
		planilha.addColunaEValor(Translator.COMPANY_NAME, "B"); // Assumindo Translator.COMPANY_NAME
		planilha.addColunaEValor(Translator.CONTACT_PERSON, "D"); // Assumindo Translator.CONTACT_PERSON
		planilha.addColunaEValor(Translator.EMAIL, "E");
		planilha.addColunaEValor(Translator.ADDRESS, "G"); // Assumindo Translator.ADDRESS

		planilhaMap.put(TipoPlanilhaImportacaoEnum.SUPPLIERS, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapEmployees() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.EMPLOYEES.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.EMPLOYEES);

		// Mapeamento baseado no OldEmployeeImporter: "A", "B", "C", "D", "E", "F"
		planilha.addColunaEValor(Translator.ID, "A");
		planilha.addColunaEValor(Translator.NAME, "B");
		planilha.addColunaEValor(Translator.EMAIL, "C");
		planilha.addColunaEValor(Translator.DEPARTMENT, "D"); // Assumindo Translator.DEPARTMENT
		planilha.addColunaEValor(Translator.POSITION, "E"); // Assumindo Translator.POSITION
		planilha.addColunaEValor(Translator.HIRE_DATE, "F"); // Assumindo Translator.HIRE_DATE

		planilhaMap.put(TipoPlanilhaImportacaoEnum.EMPLOYEES, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapOrders() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.ORDERS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.ORDERS);

		// Mapeamento baseado no OldOrderImporter: "A", "B", "C", "D", "E"
		planilha.addColunaEValor(Translator.ORDER_ID, "A"); // Assumindo Translator.ORDER_ID
		planilha.addColunaEValor(Translator.CUSTOMER_ID, "B"); // Assumindo Translator.CUSTOMER_ID
		planilha.addColunaEValor(Translator.ORDER_DATE, "C"); // Assumindo Translator.ORDER_DATE
		planilha.addColunaEValor(Translator.TOTAL_VALUE, "D"); // Assumindo Translator.TOTAL_VALUE
		planilha.addColunaEValor(Translator.STATUS, "E");

		planilhaMap.put(TipoPlanilhaImportacaoEnum.ORDERS, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapInventory() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.INVENTORY.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.INVENTORY);

		// Mapeamento baseado no OldInventoryImporter: "A", "B", "C", "D"
		planilha.addColunaEValor(Translator.PRODUCT_ID, "A"); // Assumindo Translator.PRODUCT_ID
		planilha.addColunaEValor(Translator.WAREHOUSE_ID, "B"); // Assumindo Translator.WAREHOUSE_ID
		planilha.addColunaEValor(Translator.QUANTITY, "C");
		planilha.addColunaEValor(Translator.LOCATION_CODE, "D"); // Assumindo Translator.LOCATION_CODE

		planilhaMap.put(TipoPlanilhaImportacaoEnum.INVENTORY, planilha);
		return planilhaMap;
	}


	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapShipments() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.SHIPMENTS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.SHIPMENTS);

		// Mapeamento baseado no OldShipmentImporter: "A", "B", "C", "D", "E", "F"
		planilha.addColunaEValor(Translator.SHIPMENT_ID, "A"); // Assumindo Translator.SHIPMENT_ID
		planilha.addColunaEValor(Translator.ORDER_ID, "B");
		planilha.addColunaEValor(Translator.CARRIER, "C"); // Assumindo Translator.CARRIER
		planilha.addColunaEValor(Translator.TRACKING_CODE, "D"); // Assumindo Translator.TRACKING_CODE
		planilha.addColunaEValor(Translator.STATUS, "E");
		planilha.addColunaEValor(Translator.ESTIMATED_DELIVERY_DATE, "F"); // Assumindo Translator.ESTIMATED_DELIVERY_DATE

		planilhaMap.put(TipoPlanilhaImportacaoEnum.SHIPMENTS, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapAssets() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.ASSETS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.ASSETS);

		// Mapeamento baseado no OldAssetImporter: "A", "B", "C", "D", "E", "F"
		planilha.addColunaEValor(Translator.ASSET_TAG, "A"); // Assumindo Translator.ASSET_TAG
		planilha.addColunaEValor(Translator.DESCRIPTION, "B");
		planilha.addColunaEValor(Translator.CATEGORY, "C");
		planilha.addColunaEValor(Translator.EMPLOYEE_ID, "D"); // Assumindo Translator.EMPLOYEE_ID
		planilha.addColunaEValor(Translator.PURCHASE_DATE, "E"); // Assumindo Translator.PURCHASE_DATE
		planilha.addColunaEValor(Translator.STATUS, "F");

		planilhaMap.put(TipoPlanilhaImportacaoEnum.ASSETS, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapTasks() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.TASKS.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.TASKS);

		// Mapeamento baseado no OldTaskImporter: "A", "B", "C", "D", "E", "F"
		planilha.addColunaEValor(Translator.TASK_ID, "A"); // Assumindo Translator.TASK_ID
		planilha.addColunaEValor(Translator.PROJECT_ID, "B"); // Assumindo Translator.PROJECT_ID
		planilha.addColunaEValor(Translator.DESCRIPTION, "C");
		planilha.addColunaEValor(Translator.ASSIGNEE_ID, "D"); // Assumindo Translator.ASSIGNEE_ID
		planilha.addColunaEValor(Translator.PRIORITY, "E"); // Assumindo Translator.PRIORITY
		planilha.addColunaEValor(Translator.DUE_DATE, "F"); // Assumindo Translator.DUE_DATE

		planilhaMap.put(TipoPlanilhaImportacaoEnum.TASKS, planilha);
		return planilhaMap;
	}

	private static HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> mapWarehouses() {
		HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
		PlanilhaModel planilha = new PlanilhaModel();
		planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.WAREHOUSES.name());
		planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.WAREHOUSES);

		// Mapeamento baseado no OldWarehouseImporter: "A", "B", "C", "D"
		planilha.addColunaEValor(Translator.WAREHOUSE_ID, "A");
		planilha.addColunaEValor(Translator.NAME, "B");
		planilha.addColunaEValor(Translator.CITY, "C");
		planilha.addColunaEValor(Translator.CAPACITY, "D"); // Assumindo Translator.CAPACITY

		planilhaMap.put(TipoPlanilhaImportacaoEnum.WAREHOUSES, planilha);
		return planilhaMap;
	}

}