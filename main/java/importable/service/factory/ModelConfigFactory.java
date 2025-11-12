package importable.service.factory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map; 

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.mapper.AddressImportationMapper;
import importable.mapper.AssetImportationMapper;
import importable.mapper.CustomerImportationMapper;
import importable.mapper.EmployeeImportationMapper;
import importable.mapper.FinancialTransactionImportationMapper;
import importable.mapper.InterfaceSheetMapper;
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
import importable.run.MainDP;
import importable.translator.Translator;

public class ModelConfigFactory {

	/**
	 * @param type sheet type
	 * @return model configuration
	 */
	public static InterfaceSheetMapper<?> getModelConfig(SheetTypeEnum type) {
		switch (type) {
		case PRODUCTS:
			return (InterfaceSheetMapper<?>) new ProductImportationMapper(Product.class);
		case CUSTOMERS:
			return (InterfaceSheetMapper<?>) new CustomerImportationMapper(Customer.class);
		case SUPPLIERS:
			return (InterfaceSheetMapper<?>) new SupplierImportationMapper(Supplier.class);
		case EMPLOYEES:
			return (InterfaceSheetMapper<?>) new EmployeeImportationMapper(Employee.class);
		case ORDERS:
			return (InterfaceSheetMapper<?>) new OrderImportationMapper(Order.class);
		case INVENTORY:
			return (InterfaceSheetMapper<?>) new InventoryImportationMapper(Inventory.class);
		case SHIPMENTS:
			return (InterfaceSheetMapper<?>) new ShipmentImportationMapper(Shipment.class);
		case ASSETS:
			return (InterfaceSheetMapper<?>) new AssetImportationMapper(CompanyAsset.class);
		case TASKS:
			return (InterfaceSheetMapper<?>) new TaskImportationMapper(Task.class);
		case WAREHOUSES:
			return (InterfaceSheetMapper<?>) new WarehouseImportationMapper(Warehouse.class);
		case ADDRESS:
			return (InterfaceSheetMapper<?>) new AddressImportationMapper(Address.class);
		case FINANCIALTRANSACTION:
			return (InterfaceSheetMapper<?>) new FinancialTransactionImportationMapper(FinancialTransaction.class);
		case PROJECT:
			return (InterfaceSheetMapper<?>) new ProjectImportationMapper(Project.class);
		default:
			throw new IllegalArgumentException("Service not found for type: " + type);
		}
	}

	/**
	 * @param type sheet type
	 * @return an InputStream for the resource file
	 */
	public static InputStream getResourceAsStream(SheetTypeEnum type) {
		switch (type) {
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
			throw new IllegalArgumentException("Service not found for type: " + type);
		}
	}

	/**
	 * @param type sheet type
	 * @return a map with the SheetModel configuration
	 */
	public static Map<SheetTypeEnum, SheetModel> generateSheetModel(SheetTypeEnum type) {
		switch (type) {
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
			throw new IllegalArgumentException("Service not found for type: " + type);
		}
	}

	private static Map<SheetTypeEnum, SheetModel> mapCustomers() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.CUSTOMERS.name());
		sheetModel.setLogicalType(SheetTypeEnum.CUSTOMERS);

		sheetModel.addColumnValue(Translator.ID, "A");
		sheetModel.addColumnValue(Translator.NAME, "B");
		sheetModel.addColumnValue(Translator.EMAIL, "D");
		sheetModel.addColumnValue(Translator.CITY, "F");
		sheetModel.addColumnValue(Translator.STATE, "G");

		sheetModelMap.put(SheetTypeEnum.CUSTOMERS, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapProducts() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.PRODUCTS.name());
		sheetModel.setLogicalType(SheetTypeEnum.PRODUCTS);

		sheetModel.addColumnValue(Translator.ID, "A");
		sheetModel.addColumnValue(Translator.NAME, "B");
		sheetModel.addColumnValue(Translator.PRICE, "D"); 
		sheetModel.addColumnValue(Translator.CATEGORY, "C");

		sheetModelMap.put(SheetTypeEnum.PRODUCTS, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapSuppliers() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.SUPPLIERS.name());
		sheetModel.setLogicalType(SheetTypeEnum.SUPPLIERS);

		sheetModel.addColumnValue(Translator.ID, "A");
		sheetModel.addColumnValue(Translator.COMPANY_NAME, "B"); 
		sheetModel.addColumnValue(Translator.CONTACT_PERSON, "D"); 
		sheetModel.addColumnValue(Translator.EMAIL, "E");
		sheetModel.addColumnValue(Translator.ADDRESS, "G"); 

		sheetModelMap.put(SheetTypeEnum.SUPPLIERS, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapEmployees() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.EMPLOYEES.name());
		sheetModel.setLogicalType(SheetTypeEnum.EMPLOYEES);

		sheetModel.addColumnValue(Translator.ID, "A");
		sheetModel.addColumnValue(Translator.NAME, "B");
		sheetModel.addColumnValue(Translator.EMAIL, "C");
		sheetModel.addColumnValue(Translator.DEPARTMENT, "D"); 
		sheetModel.addColumnValue(Translator.POSITION, "E"); 
		sheetModel.addColumnValue(Translator.HIRE_DATE, "F");

		sheetModelMap.put(SheetTypeEnum.EMPLOYEES, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapOrders() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.ORDERS.name());
		sheetModel.setLogicalType(SheetTypeEnum.ORDERS);

		sheetModel.addColumnValue(Translator.ORDER_ID, "A");
		sheetModel.addColumnValue(Translator.CUSTOMER_ID, "B");
		sheetModel.addColumnValue(Translator.ORDER_DATE, "C");
		sheetModel.addColumnValue(Translator.TOTAL_VALUE, "D"); 
		sheetModel.addColumnValue(Translator.STATUS, "E");

		sheetModelMap.put(SheetTypeEnum.ORDERS, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapInventory() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.INVENTORY.name());
		sheetModel.setLogicalType(SheetTypeEnum.INVENTORY);

		sheetModel.addColumnValue(Translator.PRODUCT_ID, "A"); 
		sheetModel.addColumnValue(Translator.WAREHOUSE_ID, "B"); 
		sheetModel.addColumnValue(Translator.QUANTITY, "C");
		sheetModel.addColumnValue(Translator.LOCATION_CODE, "D"); 

		sheetModelMap.put(SheetTypeEnum.INVENTORY, sheetModel);
		return sheetModelMap;
	}


	private static Map<SheetTypeEnum, SheetModel> mapShipments() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.SHIPMENTS.name());
		sheetModel.setLogicalType(SheetTypeEnum.SHIPMENTS);

		sheetModel.addColumnValue(Translator.SHIPMENT_ID, "A"); 
		sheetModel.addColumnValue(Translator.ORDER_ID, "B");
		sheetModel.addColumnValue(Translator.CARRIER, "C");
		sheetModel.addColumnValue(Translator.TRACKING_CODE, "D");
		sheetModel.addColumnValue(Translator.STATUS, "E");
		sheetModel.addColumnValue(Translator.ESTIMATED_DELIVERY_DATE, "F");

		sheetModelMap.put(SheetTypeEnum.SHIPMENTS, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapAssets() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.ASSETS.name());
		sheetModel.setLogicalType(SheetTypeEnum.ASSETS);

		sheetModel.addColumnValue(Translator.ASSET_TAG, "A"); 
		sheetModel.addColumnValue(Translator.DESCRIPTION, "B");
		sheetModel.addColumnValue(Translator.CATEGORY, "C");
		sheetModel.addColumnValue(Translator.EMPLOYEE_ID, "D");
		sheetModel.addColumnValue(Translator.PURCHASE_DATE, "E"); 
		sheetModel.addColumnValue(Translator.STATUS, "F");

		sheetModelMap.put(SheetTypeEnum.ASSETS, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapTasks() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.TASKS.name());
		sheetModel.setLogicalType(SheetTypeEnum.TASKS);

		sheetModel.addColumnValue(Translator.TASK_ID, "A"); 
		sheetModel.addColumnValue(Translator.PROJECT_ID, "B"); 
		sheetModel.addColumnValue(Translator.DESCRIPTION, "C");
		sheetModel.addColumnValue(Translator.ASSIGNEE_ID, "D");
		sheetModel.addColumnValue(Translator.PRIORITY, "E");
		sheetModel.addColumnValue(Translator.DUE_DATE, "F"); 

		sheetModelMap.put(SheetTypeEnum.TASKS, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapWarehouses() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.WAREHOUSES.name());
		sheetModel.setLogicalType(SheetTypeEnum.WAREHOUSES);

		sheetModel.addColumnValue(Translator.WAREHOUSE_ID, "A");
		sheetModel.addColumnValue(Translator.NAME, "B");
		sheetModel.addColumnValue(Translator.CITY, "C");
		sheetModel.addColumnValue(Translator.CAPACITY, "D"); // Assuming Translator.CAPACITY exists

		sheetModelMap.put(SheetTypeEnum.WAREHOUSES, sheetModel);
		return sheetModelMap;
	}

	private static Map<SheetTypeEnum, SheetModel> mapAddress() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.ADDRESS.name());
		sheetModel.setLogicalType(SheetTypeEnum.ADDRESS);

		// NOTE: Translator keys are left as-is since the Translator class was not provided
		sheetModel.addColumnValue(Translator.LOGRADOURO, "A");
		sheetModel.addColumnValue(Translator.NUMERO, "B");
		sheetModel.addColumnValue(Translator.COMPLEMENTO, "C");
		sheetModel.addColumnValue(Translator.BAIRRO, "D");
		sheetModel.addColumnValue(Translator.CIDADE, "E");
		sheetModel.addColumnValue(Translator.ESTADO, "F");
		sheetModel.addColumnValue(Translator.CEP, "G");

		sheetModelMap.put(SheetTypeEnum.ADDRESS, sheetModel);
		return sheetModelMap;
	}
	
	private static Map<SheetTypeEnum, SheetModel> mapFinancialTransaction() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.FINANCIALTRANSACTION.name());
		sheetModel.setLogicalType(SheetTypeEnum.FINANCIALTRANSACTION);

		sheetModel.addColumnValue(Translator.ID, "A");
		sheetModel.addColumnValue(Translator.TYPE, "B");
		sheetModel.addColumnValue(Translator.CATEGORY, "C");
		sheetModel.addColumnValue(Translator.DESCRIPTION, "D");
		sheetModel.addColumnValue(Translator.AMOUNT, "E");
		sheetModel.addColumnValue(Translator.CURRENCY, "F");
		sheetModel.addColumnValue(Translator.TRANSACTION_DATE, "G");
		sheetModel.addColumnValue(Translator.PAYMENT_METHOD, "H");
		sheetModel.addColumnValue(Translator.STATUS, "I");
		sheetModel.addColumnValue(Translator.SOURCE_ACCOUNT, "J");
		sheetModel.addColumnValue(Translator.DESTINATION_ACCOUNT, "K");
		sheetModel.addColumnValue(Translator.REFERENCE_NUMBER, "L");
		sheetModel.addColumnValue(Translator.RELATED_ENTITY_ID, "M");
		sheetModel.addColumnValue(Translator.RELATED_ENTITY_TYPE, "N");
		sheetModel.addColumnValue(Translator.NOTES, "O");
		sheetModel.addColumnValue(Translator.CREATED_BY, "P");
		sheetModel.addColumnValue(Translator.RECORDED_DATE, "Q");

		sheetModelMap.put(SheetTypeEnum.FINANCIALTRANSACTION, sheetModel);
		return sheetModelMap;
	}
	
	private static Map<SheetTypeEnum, SheetModel> mapProject() {
		Map<SheetTypeEnum, SheetModel> sheetModelMap = new HashMap<>();
		SheetModel sheetModel = new SheetModel();
		sheetModel.setSheetName("0 - " + SheetTypeEnum.PROJECT.name());
		sheetModel.setLogicalType(SheetTypeEnum.PROJECT);

		sheetModel.addColumnValue(Translator.ID, "A");
		sheetModel.addColumnValue(Translator.NAME, "B");
		sheetModel.addColumnValue(Translator.DESCRIPTION, "C");
		sheetModel.addColumnValue(Translator.PROJECT_MANAGER, "D");
		sheetModel.addColumnValue(Translator.STATUS, "E");
		sheetModel.addColumnValue(Translator.PRIORITY, "F");
		sheetModel.addColumnValue(Translator.START_DATE, "G");
		sheetModel.addColumnValue(Translator.EXPECTED_END_DATE, "H");
		sheetModel.addColumnValue(Translator.ACTUAL_END_DATE, "I");
		sheetModel.addColumnValue(Translator.ALLOCATED_BUDGET, "J");
		sheetModel.addColumnValue(Translator.ACTUAL_SPENT, "K");
		sheetModel.addColumnValue(Translator.CLIENT, "L");
		sheetModel.addColumnValue(Translator.CATEGORY, "M");
		sheetModel.addColumnValue(Translator.COMPLETION_PERCENTAGE, "N");
		sheetModel.addColumnValue(Translator.RISK_LEVEL, "O");

		sheetModelMap.put(SheetTypeEnum.PROJECT, sheetModel);
		return sheetModelMap;
	}
	
}