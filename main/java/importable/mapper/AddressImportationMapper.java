package importable.mapper;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.Address;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class AddressImportationMapper extends GenericImportMapper<Address> {

    public AddressImportationMapper(Class<Address> tipo) {
        super(tipo);
    }

    @Override
    public Function<RowData, ArrayList<Address>> processRow() {
        ArrayList<Address> modelos = new ArrayList<>();
        return (row) -> {
            Address address = criarInstancia();

            address.setLogradouro(getStringValue(row, Translator.LOGRADOURO));
            address.setNumero(getStringValue(row, Translator.NUMERO));
            address.setComplemento(getStringValue(row, Translator.COMPLEMENTO));
            address.setBairro(getStringValue(row, Translator.BAIRRO));
            address.setCidade(getStringValue(row, Translator.CIDADE));
            address.setEstado(getStringValue(row, Translator.ESTADO));
            address.setCep(getStringValue(row, Translator.CEP));
            
            modelos.add(address);
            return modelos;
        };
    }

    @Override
    public void validate(Address address, RowData row) throws ProcessamentoArquivoException {
        if (address.getLogradouro() == null || address.getLogradouro().isEmpty()) {
            // throw new ProcessamentoArquivoException("A coluna 'logradouro' é obrigatória.", row.getLineNumber());
        }
         if (address.getCidade() == null || address.getCidade().isEmpty()) {
            // throw new ProcessamentoArquivoException("A coluna 'cidade' é obrigatória.", row.getLineNumber());
        }
         if (address.getCep() == null || address.getCep().isEmpty()) {
            // throw new ProcessamentoArquivoException("A coluna 'cep' é obrigatória.", row.getLineNumber());
        }
    }
}