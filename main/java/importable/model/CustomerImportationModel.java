package importable.model;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.customer.Customer;
import importable.service.SheetDataUtils;

/**
 * Model class for importing Customer data from Excel spreadsheet using column identifiers
 */
public class CustomerImportationModel implements InterfacePlanilhaModel<Customer> {

    @Override
    public Customer criarInstancia() {
        return new Customer();
    }

    @Override
    public Function<RowData, ArrayList<Customer>> createModelsByRow() {
        ArrayList<Customer> modelos = new ArrayList<Customer>();
        return (row) -> {
            Customer customer = criarInstancia();
            
            // Using column identifiers from the spreadsheet header
            customer.setId(getLongValueByIdentifier(row, "ID"));
            customer.setName(getStringValueByIdentifier(row, "Name"));
            customer.setCpf(getStringValueByIdentifier(row, "CPF"));
            customer.setEmail(getStringValueByIdentifier(row, "Email"));
            
            // BirthDate conversion from Excel serial date
            Double excelBirthDate = getDoubleValueByIdentifier(row, "BirthDate");
            if (excelBirthDate != null) {
                customer.setBirthDate(SheetDataUtils.excelSerialToLocalDate(excelBirthDate));
            }
            
            customer.setCity(getStringValueByIdentifier(row, "City"));
            customer.setState(getStringValueByIdentifier(row, "State"));
            
            // Only add if we have the essential fields
            if (customer.getId() != null && customer.getName() != null && customer.getCpf() != null) {
                modelos.add(customer);
            }
            
            return modelos;
        };
    }
    
    private Long getLongValueByIdentifier(RowData row, String columnIdentifier) {
        if (row.getCelulaByIdentificador(columnIdentifier) == null || 
            row.getCelulaByIdentificador(columnIdentifier).getValue() == null) {
            return null;
        }
        try {
            String value = row.getCelulaByIdentificador(columnIdentifier).getValue().toString();
            return value.isEmpty() ? null : Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private String getStringValueByIdentifier(RowData row, String columnIdentifier) {
        if (row.getCelulaByIdentificador(columnIdentifier) == null || 
            row.getCelulaByIdentificador(columnIdentifier).getValue() == null) {
            return null;
        }
        String value = row.getCelulaByIdentificador(columnIdentifier).getValue().toString();
        return value.isEmpty() ? null : value.trim();
    }
    
    private Double getDoubleValueByIdentifier(RowData row, String columnIdentifier) {
        if (row.getCelulaByIdentificador(columnIdentifier) == null || 
            row.getCelulaByIdentificador(columnIdentifier).getValue() == null) {
            return null;
        }
        try {
            String value = row.getCelulaByIdentificador(columnIdentifier).getValue().toString();
            return value.isEmpty() ? null : Double.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}