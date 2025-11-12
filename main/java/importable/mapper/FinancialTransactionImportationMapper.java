package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.FinancialTransaction;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.FileProcessingException;

public class FinancialTransactionImportationMapper extends GenericImportMapper<FinancialTransaction> {

    public FinancialTransactionImportationMapper(Class<FinancialTransaction> tipo) {
        super(tipo);
    }

    @Override
    public Function<RowData, ArrayList<FinancialTransaction>> processRow() {
        ArrayList<FinancialTransaction> modelos = new ArrayList<>();
        return (row) -> {
            FinancialTransaction transaction = createInstance();

            transaction.setId(getLongValue(row, Translator.ID));
            transaction.setType(getStringValue(row, Translator.TYPE));
            transaction.setCategory(getStringValue(row, Translator.CATEGORY));
            transaction.setDescription(getStringValue(row, Translator.DESCRIPTION));
            transaction.setCurrency(getStringValue(row, Translator.CURRENCY));
            transaction.setPaymentMethod(getStringValue(row, Translator.PAYMENT_METHOD));
            transaction.setStatus(getStringValue(row, Translator.STATUS));
            transaction.setSourceAccount(getStringValue(row, Translator.SOURCE_ACCOUNT));
            transaction.setDestinationAccount(getStringValue(row, Translator.DESTINATION_ACCOUNT));
            transaction.setReferenceNumber(getStringValue(row, Translator.REFERENCE_NUMBER));
            transaction.setRelatedEntityId(getLongValue(row, Translator.RELATED_ENTITY_ID));
            transaction.setRelatedEntityType(getStringValue(row, Translator.RELATED_ENTITY_TYPE));
            transaction.setNotes(getStringValue(row, Translator.NOTES));
            transaction.setCreatedBy(getStringValue(row, Translator.CREATED_BY));

            // Tratamento para BigDecimal (assumindo que getBigDecimalValue existe)
            Double amount = getDoubleValue(row, Translator.AMOUNT);
            if (amount != null) {
                transaction.setAmount(amount);
            }

            // Tratamento para LocalDate (como no seu exemplo)
            LocalDate transactionDate = getLocalDateValue(row, Translator.TRANSACTION_DATE);
            if (transactionDate != null) {
                transaction.setTransactionDate(transactionDate);
            }

            LocalDate recordedDate = getLocalDateValue(row, Translator.RECORDED_DATE);
            if (recordedDate != null) {
                transaction.setRecordedDate(recordedDate);
            }

            modelos.add(transaction);
            return modelos;
        };
    }

    @Override
    public void validate(FinancialTransaction transaction, RowData row) throws FileProcessingException {
        // Exemplo de validações de regra de negócio:
        if (transaction.getAmount() == null) {
            // Exemplo de como lançar um erro (depende da sua implementação)
            // throw new ProcessamentoArquivoException("A coluna 'amount' é obrigatória.", row.getLineNumber());
        }
        if (transaction.getTransactionDate() == null) {
            // throw new ProcessamentoArquivoException("A coluna 'transactionDate' é obrigatória.", row.getLineNumber());
        }
         if (transaction.getType() == null || transaction.getType().isEmpty()) {
            // throw new ProcessamentoArquivoException("A coluna 'type' é obrigatória.", row.getLineNumber());
        }
        // Adicione outras validações conforme necessário...
    }
}