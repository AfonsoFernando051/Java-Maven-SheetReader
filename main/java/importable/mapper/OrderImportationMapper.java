package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.order.Order;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class OrderImportationMapper extends GenericImportMapper<Order> {

    public OrderImportationMapper(Class<Order> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Order>> processRow() {
        ArrayList<Order> modelos = new ArrayList<Order>();
        return (row) -> {
            Order order = criarInstancia();
            
            order.setOrderId(getLongValue(row, Translator.ORDER_ID));
            order.setCustomerId(getLongValue(row, Translator.CUSTOMER_ID));
            order.setTotalValue(getDoubleValue(row, Translator.TOTAL_VALUE));
            order.setStatus(getStringValue(row, Translator.STATUS));
            
            LocalDate orderDate = getLocalDateValue(row, Translator.ORDER_DATE);
            if (orderDate != null) {
                order.setOrderDate(orderDate);
            }
            
            modelos.add(order);
            return modelos;
        };
    }

	@Override
	public void validate(Order object, RowData row) throws ProcessamentoArquivoException {
		// Validações
	}
}