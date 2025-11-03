package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.Employee;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class EmployeeImportationMapper extends GenericImportMapper<Employee> {

    public EmployeeImportationMapper(Class<Employee> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Employee>> processRow() {
        ArrayList<Employee> modelos = new ArrayList<Employee>();
        return (row) -> {
            Employee employee = criarInstancia();
            
            employee.setId(getLongValue(row, Translator.ID));
            employee.setName(getStringValue(row, Translator.NAME));
            employee.setEmail(getStringValue(row, Translator.EMAIL));
            employee.setDepartment(getStringValue(row, Translator.DEPARTMENT));
            employee.setPosition(getStringValue(row, Translator.POSITION));

            LocalDate hireDate = getLocalDateValue(row, Translator.HIRE_DATE);
            if (hireDate != null) {
                employee.setHireDate(hireDate);
            }
            
            modelos.add(employee);
            return modelos;
        };
    }

	@Override
	public void validate(Employee object, RowData row) throws ProcessamentoArquivoException {
		// Validações
	}
}