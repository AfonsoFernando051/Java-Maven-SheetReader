package importable.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.product.Product;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

/**
 * @author Fernando Dias
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
	         
	         product.setId(getLongValue(row, Translator.ID));
	         product.setName(getStringValue(row, Translator.NAME));
	         product.setCategory(getStringValue(row, Translator.CATEGORY));
	         product.setPrice(getDoubleValue(row, Translator.PRICE));
	         product.setQuantity(getIntegerValue(row, Translator.QUANTITY));
	            
	         LocalDate excelDate = getLocalDateValue(row, Translator.REGISTRATION_DATE);
	         if (excelDate != null) {
	             product.setRegistrationDate(excelDate);
	         }
	         
	         modelos.add(product);
	         
	         return modelos;
	     };
	 }

	@Override
	public void validate(Product object, RowData row) throws ProcessamentoArquivoException {
		
	}

	 
}
