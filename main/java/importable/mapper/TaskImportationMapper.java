package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.Task;
import importable.model.row.RowData;
import importable.translator.Translator;
import importable.utils.ProcessamentoArquivoException;

public class TaskImportationMapper extends GenericImportMapper<Task> {

    public TaskImportationMapper(Class<Task> tipo) {
		super(tipo);
	}

	@Override
    public Function<RowData, ArrayList<Task>> processRow() {
        ArrayList<Task> modelos = new ArrayList<Task>();
        return (row) -> {
            Task task = criarInstancia();
            
            task.setTaskId(getLongValue(row, Translator.TASK_ID));
            task.setProjectId(getLongValue(row, Translator.PROJECT_ID));
            task.setDescription(getStringValue(row, Translator.DESCRIPTION));
            task.setAssigneeId(getLongValue(row, Translator.ASSIGNEE_ID));
            task.setPriority(getStringValue(row, Translator.PRIORITY));
            
            LocalDate dueDate = getLocalDateValue(row, Translator.DUE_DATE);
            if (dueDate != null) {
                task.setDueDate(dueDate);
            }
            
            modelos.add(task);
            return modelos;
        };
    }

	@Override
	public void validate(Task object, RowData row) throws ProcessamentoArquivoException {
		// Validações
	}
}