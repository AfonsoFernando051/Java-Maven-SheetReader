package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.Project;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.FileProcessingException;

public class ProjectImportationMapper extends GenericImportMapper<Project> {

    public ProjectImportationMapper(Class<Project> tipo) {
        super(tipo);
    }

    @Override
    public Function<RowData, ArrayList<Project>> processRow() {
        ArrayList<Project> modelos = new ArrayList<>();
        return (row) -> {
            Project project = createInstance();

            project.setId(getLongValue(row, Translator.ID));
            project.setName(getStringValue(row, Translator.NAME));
            project.setDescription(getStringValue(row, Translator.DESCRIPTION));
            project.setProjectManager(getStringValue(row, Translator.PROJECT_MANAGER));
            project.setStatus(getStringValue(row, Translator.STATUS));
            project.setPriority(getStringValue(row, Translator.PRIORITY));
            project.setClient(getStringValue(row, Translator.CLIENT));
            project.setCategory(getStringValue(row, Translator.CATEGORY));
            project.setRiskLevel(getStringValue(row, Translator.RISK_LEVEL));

            // Tratamento para Datas
            LocalDate startDate = getLocalDateValue(row, Translator.START_DATE);
            if (startDate != null) {
                project.setStartDate(startDate);
            }

            LocalDate expectedEndDate = getLocalDateValue(row, Translator.EXPECTED_END_DATE);
            if (expectedEndDate != null) {
                project.setExpectedEndDate(expectedEndDate);
            }
            
            LocalDate actualEndDate = getLocalDateValue(row, Translator.ACTUAL_END_DATE);
            if (actualEndDate != null) {
                project.setActualEndDate(actualEndDate);
            }

            // Tratamento para BigDecimal (assumindo que getBigDecimalValue existe)
            Double allocatedBudget = getDoubleValue(row, Translator.ALLOCATED_BUDGET);
            if (allocatedBudget != null) {
                project.setAllocatedBudget(allocatedBudget);
            }

            Double actualSpent = getDoubleValue(row, Translator.ACTUAL_SPENT);
            if (actualSpent != null) {
                project.setActualSpent(actualSpent);
            }

            // Tratamento para Integer (assumindo que getIntegerValue existe)
            Integer completion = getIntegerValue(row, Translator.COMPLETION_PERCENTAGE);
            if (completion != null) {
                project.setCompletionPercentage(completion);
            }
            
            modelos.add(project);
            return modelos;
        };
    }

    @Override
    public void validate(Project project, RowData row) throws FileProcessingException {
        // Exemplo de validações:
        if (project.getName() == null || project.getName().isEmpty()) {
            // throw new ProcessamentoArquivoException("A coluna 'name' (Nome do Projeto) é obrigatória.", row.getLineNumber());
        }
         if (project.getProjectManager() == null || project.getProjectManager().isEmpty()) {
            // throw new ProcessamentoArquivoException("A coluna 'projectManager' é obrigatória.", row.getLineNumber());
        }
        if (project.getStartDate() == null) {
            // throw new ProcessamentoArquivoException("A coluna 'startDate' é obrigatória.", row.getLineNumber());
        }
    }
}