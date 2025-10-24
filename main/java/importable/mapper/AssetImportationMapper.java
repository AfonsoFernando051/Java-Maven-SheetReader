package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.asset.CompanyAsset;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class AssetImportationMapper extends GenericImportMapper<CompanyAsset> {

    public AssetImportationMapper(Class<CompanyAsset> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<CompanyAsset>> processRow() {
        ArrayList<CompanyAsset> modelos = new ArrayList<CompanyAsset>();
        return (row) -> {
            CompanyAsset asset = criarInstancia();
            
            asset.setAssetTag(getStringValue(row, Translator.ASSET_TAG));
            asset.setDescription(getStringValue(row, Translator.DESCRIPTION));
            asset.setCategory(getStringValue(row, Translator.CATEGORY));
            asset.setEmployeeId(getLongValue(row, Translator.EMPLOYEE_ID));
            asset.setStatus(getStringValue(row, Translator.STATUS));
            
            LocalDate purchaseDate = getLocalDateValue(row, Translator.PURCHASE_DATE);
            if (purchaseDate != null) {
                asset.setPurchaseDate(purchaseDate);
            }
            
            modelos.add(asset);
            return modelos;
        };
    }

	@Override
	public void validate(CompanyAsset object, RowData row) throws ProcessamentoArquivoException {
		// Validações
	}
}