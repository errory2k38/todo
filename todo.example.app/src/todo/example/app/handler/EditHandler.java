
package todo.example.app.handler;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import todo.example.app.TodoConstants;
import todo.example.app.edit.TodoEditDialog;
import todo.example.app.edit.TodoEditService;
import todo.example.app.model.Todo;

public class EditHandler {

	@Execute
	public void execute(IEclipseContext context, @Named(TodoConstants.SELECTED_TODO_ITEM) Todo todo) {
		IEclipseContext child = context.createChild("TodoEditContext");
		try {
			TodoEditService service = ContextInjectionFactory.make(TodoEditService.class, child);
			service.setUpdateTodo(todo);
			child.set(TodoEditService.class, service);
			TodoEditDialog dialog = ContextInjectionFactory.make(TodoEditDialog.class, child);
			dialog.showAndWait();
		} finally {
			child.dispose();
		}
	}

	@CanExecute
	public boolean canExecute(@Named(TodoConstants.SELECTED_TODO_ITEM) Todo todo) {
		return todo != null;
	}

}