package importable.service.factory;

import importable.config.SheetTypeEnum;
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
import importable.service.ImportService;
import importable.service.ImportSheetService;

/**
 * Factory for import services
 */
public class ImportServiceFactory {

	@SuppressWarnings("unchecked")
	public static <T> ImportService<T> getService(Class<T> entityClass) {
		if (entityClass.equals(importable.model.Product.class)) {
			return (ImportService<T>) new ImportSheetService<Product>();
		} else if (entityClass.equals(importable.model.Customer.class)) {
			return (ImportService<T>) new ImportSheetService<Customer>();
		} else if (entityClass.equals(importable.model.Supplier.class)) {
			return (ImportService<T>) new ImportSheetService<Supplier>();
		} else if (entityClass.equals(importable.model.Employee.class)) {
			return (ImportService<T>) new ImportSheetService<Employee>();
		} else if (entityClass.equals(importable.model.Order.class)) {
			return (ImportService<T>) new ImportSheetService<Order>();
		} else if (entityClass.equals(importable.model.Inventory.class)) {
			return (ImportService<T>) new ImportSheetService<Inventory>();
		} else if (entityClass.equals(importable.model.Shipment.class)) {
			return (ImportService<T>) new ImportSheetService<Shipment>();
		} else if (entityClass.equals(importable.model.CompanyAsset.class)) {
			return (ImportService<T>) new ImportSheetService<CompanyAsset>();
		} else if (entityClass.equals(importable.model.Task.class)) {
			return (ImportService<T>) new ImportSheetService<Task>();
		} else if (entityClass.equals(importable.model.Warehouse.class)) {
			return (ImportService<T>) new ImportSheetService<Warehouse>();
		}else if (entityClass.equals(importable.model.Address.class)) {
			return (ImportService<T>) new ImportSheetService<Address>();
		}else if (entityClass.equals(importable.model.FinancialTransaction.class)) {
			return (ImportService<T>) new ImportSheetService<FinancialTransaction>();
		}else if (entityClass.equals(importable.model.Project.class)) {
			return (ImportService<T>) new ImportSheetService<Project>();
		}
		throw new IllegalArgumentException("Serviço não encontrado para a classe: " + entityClass);
	}

	public static ImportService<?> getServiceByType(SheetTypeEnum tipo) {
		switch (tipo) {
		case PRODUCTS:
			return (ImportService<?>) new ImportSheetService<Product>();
		case CUSTOMERS:
			return (ImportService<?>) new ImportSheetService<Customer>();
		case SUPPLIERS:
			return (ImportService<?>) new ImportSheetService<Supplier>();
		case EMPLOYEES:
			return (ImportService<?>) new ImportSheetService<Employee>();
		case ORDERS:
			return (ImportService<?>) new ImportSheetService<Order>();
		case INVENTORY:
			return (ImportService<?>) new ImportSheetService<Inventory>();
		case SHIPMENTS:
			return (ImportService<?>) new ImportSheetService<Shipment>();
		case ASSETS:
			return (ImportService<?>) new ImportSheetService<CompanyAsset>();
		case TASKS:
			return (ImportService<?>) new ImportSheetService<Task>();
		case WAREHOUSES:
			return (ImportService<?>) new ImportSheetService<Warehouse>();
		case ADDRESS:
			return (ImportService<?>) new ImportSheetService<Address>();
		case FINANCIALTRANSACTION:
			return (ImportService<?>) new ImportSheetService<FinancialTransaction>();
		case PROJECT:
			return (ImportService<?>) new ImportSheetService<Project>();
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}
}