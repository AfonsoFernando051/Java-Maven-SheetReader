package importable.service.factory;

import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.asset.CompanyAsset;
import importable.model.customer.Customer;
import importable.model.employee.Employee;
import importable.model.inventory.Inventory;
import importable.model.order.Order;
import importable.model.product.Product;
import importable.model.shipment.Shipment;
import importable.model.supplier.Supplier;
import importable.model.task.Task;
import importable.model.warehouse.Warehouse;
import importable.service.ImportService;
import importable.service.ImportSheetService;

/**
 * Factory for import services
 */
public class ImportServiceFactory {

	@SuppressWarnings("unchecked")
	public static <T> ImportService<T> getService(Class<T> entityClass) {
		if (entityClass.equals(importable.model.product.Product.class)) {
			return (ImportService<T>) new ImportSheetService<Product>();
		} else if (entityClass.equals(importable.model.customer.Customer.class)) {
			return (ImportService<T>) new ImportSheetService<Customer>();
		} else if (entityClass.equals(importable.model.supplier.Supplier.class)) {
			return (ImportService<T>) new ImportSheetService<Supplier>();
		} else if (entityClass.equals(importable.model.employee.Employee.class)) {
			return (ImportService<T>) new ImportSheetService<Employee>();
		} else if (entityClass.equals(importable.model.order.Order.class)) {
			return (ImportService<T>) new ImportSheetService<Order>();
		} else if (entityClass.equals(importable.model.inventory.Inventory.class)) {
			return (ImportService<T>) new ImportSheetService<Inventory>();
		} else if (entityClass.equals(importable.model.shipment.Shipment.class)) {
			return (ImportService<T>) new ImportSheetService<Shipment>();
		} else if (entityClass.equals(importable.model.asset.CompanyAsset.class)) {
			return (ImportService<T>) new ImportSheetService<CompanyAsset>();
		} else if (entityClass.equals(importable.model.task.Task.class)) {
			return (ImportService<T>) new ImportSheetService<Task>();
		} else if (entityClass.equals(importable.model.warehouse.Warehouse.class)) {
			return (ImportService<T>) new ImportSheetService<Warehouse>();
		}
		throw new IllegalArgumentException("Serviço não encontrado para a classe: " + entityClass);
	}

	public static ImportService<?> getServiceByType(TipoPlanilhaImportacaoEnum tipo) {
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
		default:
			throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
		}
	}
}