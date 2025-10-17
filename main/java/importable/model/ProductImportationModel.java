package importable.model;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.product.Product;
import importable.service.SheetDataUtils;
import importable.utils.ProcessamentoArquivoException;

/**
 * @author fernando.dias
 */
public class ProductImportationModel extends GenericImportModel<Product> {

	 public ProductImportationModel(Class<Product> tipo) {
		super(tipo);
	}

	 @Override
	 public Function<RowData, ArrayList<Product>> processRow() {
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

	@Override
	public void validate(Product object, RowData row) throws ProcessamentoArquivoException {
		
	}

	 
}
