package importable.mapper;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.inventory.Inventory;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class InventoryImportationMapper extends GenericImportMapper<Inventory> {

    public InventoryImportationMapper(Class<Inventory> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Inventory>> processRow() {
        ArrayList<Inventory> modelos = new ArrayList<Inventory>();
        return (row) -> {
            Inventory inventory = criarInstancia();
            
            inventory.setProductId(getLongValue(row, Translator.PRODUCT_ID));
            inventory.setWarehouseId(getLongValue(row, Translator.WAREHOUSE_ID));
            inventory.setQuantity(getIntegerValue(row, Translator.QUANTITY));
            inventory.setLocationCode(getStringValue(row, Translator.LOCATION_CODE));
            
            modelos.add(inventory);
            return modelos;
        };
    }

	@Override
	public void validate(Inventory object, RowData row) throws ProcessamentoArquivoException {
	}
}