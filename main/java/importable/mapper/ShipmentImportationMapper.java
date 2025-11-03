package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.Shipment;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class ShipmentImportationMapper extends GenericImportMapper<Shipment> {

    public ShipmentImportationMapper(Class<Shipment> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Shipment>> processRow() {
        ArrayList<Shipment> modelos = new ArrayList<Shipment>();
        return (row) -> {
            Shipment shipment = criarInstancia();
            
            shipment.setShipmentId(getLongValue(row, Translator.SHIPMENT_ID));
            shipment.setOrderId(getLongValue(row, Translator.ORDER_ID));
            shipment.setCarrier(getStringValue(row, Translator.CARRIER));
            shipment.setTrackingCode(getStringValue(row, Translator.TRACKING_CODE));
            shipment.setStatus(getStringValue(row, Translator.STATUS));
            
            LocalDate deliveryDate = getLocalDateValue(row, Translator.ESTIMATED_DELIVERY_DATE);
            if (deliveryDate != null) {
                shipment.setEstimatedDeliveryDate(deliveryDate);
            }
            
            modelos.add(shipment);
            return modelos;
        };
    }

	@Override
	public void validate(Shipment object, RowData row) throws ProcessamentoArquivoException {
	}
}