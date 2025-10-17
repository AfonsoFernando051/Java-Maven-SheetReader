package importable.model;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.product.Product;
import importable.service.SheetDataUtils;

/**
 * @author fernando.dias
 */
public class ProductImportationModel implements InterfacePlanilhaModel<Product> {

	 @Override
	 public Product criarInstancia() {
	     return new Product();
	 }

	 @Override
	 public Function<RowData, ArrayList<Product>> createModelsByRow() {
	     ArrayList<Product> modelos = new ArrayList<Product>();
	     return (row) -> {
	         Product product = criarInstancia();
	         
	         // Using column identifiers instead of indices
	         product.setId(getLongValue(row, "ID"));
	         product.setName(getStringValue(row, "Name"));
	         product.setCategory(getStringValue(row, "Category"));
	         product.setPrice(getDoubleValue(row, "Price"));
	         product.setQuantity(getIntegerValue(row, "Quantity"));
	         
	         Double excelDate = getDoubleValue(row, "RegistrationDate");
	         if (excelDate != null) {
	             product.setRegistrationDate(SheetDataUtils.excelSerialToLocalDate(excelDate));
	         }
	         
	         if (product.getId() != null && product.getName() != null) {
	             modelos.add(product);
	         }
	         
	         return modelos;
	     };
	 }
	 
	 private Long getLongValue(RowData row, String columnIdentifier) {
	     return row.getCelulaByIdentificador(columnIdentifier) != null ? 
	         Long.valueOf(row.getCelulaByIdentificador(columnIdentifier).getValue().toString()) : null;
	 }
	 
	 private String getStringValue(RowData row, String columnIdentifier) {
	     return row.getCelulaByIdentificador(columnIdentifier) != null ? 
	         row.getCelulaByIdentificador(columnIdentifier).getValue().toString() : null;
	 }
	 
	 private Double getDoubleValue(RowData row, String columnIdentifier) {
	     return row.getCelulaByIdentificador(columnIdentifier) != null ? 
	         Double.valueOf(row.getCelulaByIdentificador(columnIdentifier).getValue().toString()) : null;
	 }
 
	 private Integer getIntegerValue(RowData row, String columnIdentifier) {
	     return row.getCelulaByIdentificador(columnIdentifier) != null ? 
	         Integer.valueOf(row.getCelulaByIdentificador(columnIdentifier).getValue().toString()) : null;
	 }
}
