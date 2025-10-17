package importable.model;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.customer.Customer;
import importable.service.SheetDataUtils;
import importable.utils.ProcessamentoArquivoException;

/**
 * Model class for importing Customer data from Excel spreadsheet using column identifiers
 */
public class CustomerImportationModel extends GenericImportModel<Customer> {

    public CustomerImportationModel(Class<Customer> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Customer>> processRow() {
        ArrayList<Customer> modelos = new ArrayList<Customer>();
        return (row) -> {
            Customer customer = criarInstancia();
            
            customer.setId(getLongValue(row, "ID"));
            customer.setName(getStringValue(row, "Name"));
            customer.setCpf(getStringValue(row, "CPF"));
            customer.setEmail(getStringValue(row, "Email"));
            
            Double excelBirthDate = getDoubleValue(row, "BirthDate");
            if (excelBirthDate != null) {
                customer.setBirthDate(SheetDataUtils.excelSerialToLocalDate(excelBirthDate));
            }
            
            customer.setCity(getStringValue(row, "City"));
            customer.setState(getStringValue(row, "State"));
            
            if (customer.getId() != null && customer.getName() != null && customer.getCpf() != null) {
                modelos.add(customer);
            }
            
            return modelos;
        };
    }
    
	@Override
	public void validate(Customer object, RowData row) throws ProcessamentoArquivoException {
		
	}
}