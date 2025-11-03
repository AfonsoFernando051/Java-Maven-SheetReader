package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.Customer;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

/**
 * Model class for importing Customer data from Excel spreadsheet using column identifiers
 */
public class CustomerImportationMapper extends GenericImportMapper<Customer> {

    public CustomerImportationMapper(Class<Customer> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Customer>> processRow() {
        ArrayList<Customer> modelos = new ArrayList<Customer>();
        return (row) -> {
            Customer customer = criarInstancia();
            
            customer.setId(getLongValue(row, Translator.ID));
            customer.setName(getStringValue(row,  Translator.NAME));
            customer.setCpf(getStringValue(row,  Translator.CPF));
            customer.setEmail(getStringValue(row, Translator.EMAIL));
            
            LocalDate excelBirthDate = getLocalDateValue(row, Translator.BIRTH_DATE);
            if (excelBirthDate != null) {
                customer.setBirthDate(excelBirthDate);
            }
            
            customer.setCity(getStringValue(row, Translator.CITY));
            customer.setState(getStringValue(row, Translator.STATE));
            
                modelos.add(customer);
            
            return modelos;
        };
    }
    
	@Override
	public void validate(Customer object, RowData row) throws ProcessamentoArquivoException {
		
	}
}