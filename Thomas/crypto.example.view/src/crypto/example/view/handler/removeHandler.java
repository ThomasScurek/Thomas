package crypto.example.view.handler;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import crypto.api.Directory;
import crypto.api.Person;
import crypto.example.view.Activator;

public class RemoveHandler extends AbstractHandler {
  @SuppressWarnings("unused")
  private static Logger log = Logger.getLogger(RemoveHandler.class);

  private static final Directory directoryService = Activator.getDirectoryService();

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

    Object selectedObj = ((StructuredSelection) selection).getFirstElement();

    Person selectedPerson = (Person) selectedObj;

    directoryService.removePerson(selectedPerson);
    return null;
  }
}
