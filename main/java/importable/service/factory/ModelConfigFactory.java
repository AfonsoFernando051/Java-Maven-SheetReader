package importable.service.factory;

import java.io.InputStream;
import java.util.HashMap;

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.mapper.AddressImportationMapper;
// Imports dos Mappers
import importable.mapper.AssetImportationMapper;
import importable.mapper.CustomerImportationMapper;
import importable.mapper.EmployeeImportationMapper;
import importable.mapper.FinancialTransactionImportationMapper;
import importable.mapper.InterfacePlanilhaMapper;
import importable.mapper.InventoryImportationMapper;
import importable.mapper.OrderImportationMapper;
import importable.mapper.ProductImportationMapper;
import importable.mapper.ProjectImportationMapper;
import importable.mapper.ShipmentImportationMapper;
import importable.mapper.SupplierImportationMapper;
import importable.mapper.TaskImportationMapper;
import importable.mapper.WarehouseImportationMapper;
import importable.model.Address;
import importable.model.CompanyAsset;
import importable.model.Customer;
import importable.model.Employee;
import importable.model.FinancialTransaction;
import importable.model.Inventory;
import importable.model.Order;
import importable.model.Product;
import importable.model.Project;
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
	public static InterfacePlanilhaMapper<?> getModelConfig(SheetTypeEnum tipo) {
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
		case ADDRESS:
			return (InterfacePlanilhaMapper<?>) new AddressImportationMapper(Address.class);
		case FINANCIALTRANSACTION:
			return (InterfacePlanilhaMapper<?>) new FinancialTransactionImportationMapper(FinancialTransaction.class);
		case PROJECT:
			return (InterfacePlanilhaMapper<?>) new ProjectImportationMapper(Project.class);
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}

	/**
	 * @param tipo de planilha
	 * @return configuração de modelo
	 */
	public static InputStream getResourceAsStream(SheetTypeEnum tipo) {
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
		case ADDRESS:
			return MainDP.class.getClassLoader().getResourceAsStream("address.xlsx");
		case FINANCIALTRANSACTION:
			return MainDP.class.getClassLoader().getResourceAsStream("financial-transaction.xlsx");
		case PROJECT:
			return MainDP.class.getClassLoader().getResourceAsStream("projects.xlsx");
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}

	/**
	 * @param tipo de planilha
	 * @return configuração de modelo
	 */
	public static HashMap<SheetTypeEnum, SheetModel> generateSheetModel(SheetTypeEnum tipo) {
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
		case ADDRESS:
			return mapAddress();
		case FINANCIALTRANSACTION:
			return mapFinancialTransaction();
		case PROJECT:
			return mapProject();
			
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapCustomers() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.CUSTOMERS.name());
		planilha.setLogicalType(SheetTypeEnum.CUSTOMERS);

		planilha.addColumnValue(Translator.ID, "A");
		planilha.addColumnValue(Translator.NAME, "B");
		planilha.addColumnValue(Translator.EMAIL, "D");
		planilha.addColumnValue(Translator.CITY, "F");
		planilha.addColumnValue(Translator.STATE, "G");

		planilhaMap.put(SheetTypeEnum.CUSTOMERS, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapProducts() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.PRODUCTS.name());
		planilha.setLogicalType(SheetTypeEnum.PRODUCTS);

		planilha.addColumnValue(Translator.ID, "A");
		planilha.addColumnValue(Translator.NAME, "B");
		planilha.addColumnValue(Translator.PRICE, "D"); 
		planilha.addColumnValue(Translator.CATEGORY, "C");

		planilhaMap.put(SheetTypeEnum.PRODUCTS, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapSuppliers() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.SUPPLIERS.name());
		planilha.setLogicalType(SheetTypeEnum.SUPPLIERS);

		planilha.addColumnValue(Translator.ID, "A");
		planilha.addColumnValue(Translator.COMPANY_NAME, "B"); 
		planilha.addColumnValue(Translator.CONTACT_PERSON, "D"); 
		planilha.addColumnValue(Translator.EMAIL, "E");
		planilha.addColumnValue(Translator.ADDRESS, "G"); 

		planilhaMap.put(SheetTypeEnum.SUPPLIERS, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapEmployees() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.EMPLOYEES.name());
		planilha.setLogicalType(SheetTypeEnum.EMPLOYEES);

		planilha.addColumnValue(Translator.ID, "A");
		planilha.addColumnValue(Translator.NAME, "B");
		planilha.addColumnValue(Translator.EMAIL, "C");
		planilha.addColumnValue(Translator.DEPARTMENT, "D"); 
		planilha.addColumnValue(Translator.POSITION, "E"); 
		planilha.addColumnValue(Translator.HIRE_DATE, "F");

		planilhaMap.put(SheetTypeEnum.EMPLOYEES, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapOrders() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.ORDERS.name());
		planilha.setLogicalType(SheetTypeEnum.ORDERS);

		planilha.addColumnValue(Translator.ORDER_ID, "A");
		planilha.addColumnValue(Translator.CUSTOMER_ID, "B");
		planilha.addColumnValue(Translator.ORDER_DATE, "C");
		planilha.addColumnValue(Translator.TOTAL_VALUE, "D"); 
		planilha.addColumnValue(Translator.STATUS, "E");

		planilhaMap.put(SheetTypeEnum.ORDERS, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapInventory() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.INVENTORY.name());
		planilha.setLogicalType(SheetTypeEnum.INVENTORY);

		planilha.addColumnValue(Translator.PRODUCT_ID, "A"); 
		planilha.addColumnValue(Translator.WAREHOUSE_ID, "B"); 
		planilha.addColumnValue(Translator.QUANTITY, "C");
		planilha.addColumnValue(Translator.LOCATION_CODE, "D"); 

		planilhaMap.put(SheetTypeEnum.INVENTORY, planilha);
		return planilhaMap;
	}


	private static HashMap<SheetTypeEnum, SheetModel> mapShipments() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.SHIPMENTS.name());
		planilha.setLogicalType(SheetTypeEnum.SHIPMENTS);

		planilha.addColumnValue(Translator.SHIPMENT_ID, "A"); 
		planilha.addColumnValue(Translator.ORDER_ID, "B");
		planilha.addColumnValue(Translator.CARRIER, "C");
		planilha.addColumnValue(Translator.TRACKING_CODE, "D");
		planilha.addColumnValue(Translator.STATUS, "E");
		planilha.addColumnValue(Translator.ESTIMATED_DELIVERY_DATE, "F");

		planilhaMap.put(SheetTypeEnum.SHIPMENTS, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapAssets() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.ASSETS.name());
		planilha.setLogicalType(SheetTypeEnum.ASSETS);

		planilha.addColumnValue(Translator.ASSET_TAG, "A"); 
		planilha.addColumnValue(Translator.DESCRIPTION, "B");
		planilha.addColumnValue(Translator.CATEGORY, "C");
		planilha.addColumnValue(Translator.EMPLOYEE_ID, "D");
		planilha.addColumnValue(Translator.PURCHASE_DATE, "E"); 
		planilha.addColumnValue(Translator.STATUS, "F");

		planilhaMap.put(SheetTypeEnum.ASSETS, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapTasks() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.TASKS.name());
		planilha.setLogicalType(SheetTypeEnum.TASKS);

		planilha.addColumnValue(Translator.TASK_ID, "A"); 
		planilha.addColumnValue(Translator.PROJECT_ID, "B"); 
		planilha.addColumnValue(Translator.DESCRIPTION, "C");
		planilha.addColumnValue(Translator.ASSIGNEE_ID, "D");
		planilha.addColumnValue(Translator.PRIORITY, "E");
		planilha.addColumnValue(Translator.DUE_DATE, "F"); 

		planilhaMap.put(SheetTypeEnum.TASKS, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapWarehouses() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.WAREHOUSES.name());
		planilha.setLogicalType(SheetTypeEnum.WAREHOUSES);

		planilha.addColumnValue(Translator.WAREHOUSE_ID, "A");
		planilha.addColumnValue(Translator.NAME, "B");
		planilha.addColumnValue(Translator.CITY, "C");
		planilha.addColumnValue(Translator.CAPACITY, "D"); // Assumindo Translator.CAPACITY

		planilhaMap.put(SheetTypeEnum.WAREHOUSES, planilha);
		return planilhaMap;
	}

	private static HashMap<SheetTypeEnum, SheetModel> mapAddress() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.ADDRESS.name());
		planilha.setLogicalType(SheetTypeEnum.ADDRESS);

		planilha.addColumnValue(Translator.LOGRADOURO, "A");
		planilha.addColumnValue(Translator.NUMERO, "B");
		planilha.addColumnValue(Translator.COMPLEMENTO, "C");
		planilha.addColumnValue(Translator.BAIRRO, "D");
		planilha.addColumnValue(Translator.CIDADE, "E");
		planilha.addColumnValue(Translator.ESTADO, "F");
		planilha.addColumnValue(Translator.CEP, "G");

		planilhaMap.put(SheetTypeEnum.ADDRESS, planilha);
		return planilhaMap;
	}
	
	private static HashMap<SheetTypeEnum, SheetModel> mapFinancialTransaction() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.FINANCIALTRANSACTION.name());
		planilha.setLogicalType(SheetTypeEnum.FINANCIALTRANSACTION);

		planilha.addColumnValue(Translator.ID, "A");
		planilha.addColumnValue(Translator.TYPE, "B");
		planilha.addColumnValue(Translator.CATEGORY, "C");
		planilha.addColumnValue(Translator.DESCRIPTION, "D");
		planilha.addColumnValue(Translator.AMOUNT, "E");
		planilha.addColumnValue(Translator.CURRENCY, "F");
		planilha.addColumnValue(Translator.TRANSACTION_DATE, "G");
		planilha.addColumnValue(Translator.PAYMENT_METHOD, "H");
		planilha.addColumnValue(Translator.STATUS, "I");
		planilha.addColumnValue(Translator.SOURCE_ACCOUNT, "J");
		planilha.addColumnValue(Translator.DESTINATION_ACCOUNT, "K");
		planilha.addColumnValue(Translator.REFERENCE_NUMBER, "L");
		planilha.addColumnValue(Translator.RELATED_ENTITY_ID, "M");
		planilha.addColumnValue(Translator.RELATED_ENTITY_TYPE, "N");
		planilha.addColumnValue(Translator.NOTES, "O");
		planilha.addColumnValue(Translator.CREATED_BY, "P");
		planilha.addColumnValue(Translator.RECORDED_DATE, "Q");

		planilhaMap.put(SheetTypeEnum.FINANCIALTRANSACTION, planilha);
		return planilhaMap;
	}
	
	private static HashMap<SheetTypeEnum, SheetModel> mapProject() {
		HashMap<SheetTypeEnum, SheetModel> planilhaMap = new HashMap<SheetTypeEnum, SheetModel>();
		SheetModel planilha = new SheetModel();
		planilha.setSheetName("0 - " + SheetTypeEnum.PROJECT.name());
		planilha.setLogicalType(SheetTypeEnum.PROJECT);

		planilha.addColumnValue(Translator.ID, "A");
		planilha.addColumnValue(Translator.NAME, "B");
		planilha.addColumnValue(Translator.DESCRIPTION, "C");
		planilha.addColumnValue(Translator.PROJECT_MANAGER, "D");
		planilha.addColumnValue(Translator.STATUS, "E");
		planilha.addColumnValue(Translator.PRIORITY, "F");
		planilha.addColumnValue(Translator.START_DATE, "G");
		planilha.addColumnValue(Translator.EXPECTED_END_DATE, "H");
		planilha.addColumnValue(Translator.ACTUAL_END_DATE, "I");
		planilha.addColumnValue(Translator.ALLOCATED_BUDGET, "J");
		planilha.addColumnValue(Translator.ACTUAL_SPENT, "K");
		planilha.addColumnValue(Translator.CLIENT, "L");
		planilha.addColumnValue(Translator.CATEGORY, "M");
		planilha.addColumnValue(Translator.COMPLETION_PERCENTAGE, "N");
		planilha.addColumnValue(Translator.RISK_LEVEL, "O");

		planilhaMap.put(SheetTypeEnum.PROJECT, planilha);
		return planilhaMap;
	}
	
}