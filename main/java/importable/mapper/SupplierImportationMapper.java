package importable.mapper;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.Supplier;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.FileProcessingException;

public class SupplierImportationMapper extends GenericImportMapper<Supplier> {

    public SupplierImportationMapper(Class<Supplier> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Supplier>> processRow() {
        ArrayList<Supplier> modelos = new ArrayList<Supplier>();
        return (row) -> {
            Supplier supplier = createInstance();
            
            supplier.setId(getLongValue(row, Translator.ID));
            supplier.setCompanyName(getStringValue(row, Translator.COMPANY_NAME));
            supplier.setContactPerson(getStringValue(row, Translator.CONTACT_PERSON));
            supplier.setEmail(getStringValue(row, Translator.EMAIL));
            supplier.setAddress(getStringValue(row, Translator.ADDRESS));
            // Adicione outros campos se necessário (ex: CNPJ, Phone)
            
            modelos.add(supplier);
            return modelos;
        };
    }

	@Override
	public void validate(Supplier object, RowData row) throws FileProcessingException {
		// Validações
	}
}