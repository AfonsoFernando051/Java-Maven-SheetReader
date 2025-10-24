package importable.mapper;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.row.RowData;
import importable.model.warehouse.Warehouse;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class WarehouseImportationMapper extends GenericImportMapper<Warehouse> {

    public WarehouseImportationMapper(Class<Warehouse> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Warehouse>> processRow() {
        ArrayList<Warehouse> modelos = new ArrayList<Warehouse>();
        return (row) -> {
            Warehouse warehouse = criarInstancia();
            
            warehouse.setWarehouseId(getLongValue(row, Translator.WAREHOUSE_ID));
            warehouse.setName(getStringValue(row, Translator.NAME));
            warehouse.setCity(getStringValue(row, Translator.CITY));
            warehouse.setCapacity(getIntegerValue(row, Translator.CAPACITY));
            
            modelos.add(warehouse);
            return modelos;
        };
    }

	@Override
	public void validate(Warehouse object, RowData row) throws ProcessamentoArquivoException {
		// Validações
	}
}