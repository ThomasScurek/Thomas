package crypto.example.view.handler;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import crypto.api.Directory;
import crypto.api.Person;
import crypto.example.view.Activator;
import crypto.example.view.views.EditorInput;
import crypto.example.view.views.InputEditor;
import crypto.example.view.views.StringConversion;

public class EditHandler extends AbstractHandler {
  @SuppressWarnings("unused")
  private static final Directory directoryService = Activator.getDirectoryService();

  private static Logger log = Logger.getLogger(EditHandler.class);
  private StringConversion myString = new StringConversion();

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

    if (selection.isEmpty()) return null;

    Object selectedObj = (selection).getFirstElement();
    if (!(selectedObj instanceof Person)) return null;

    Person selectedPerson = (Person) selectedObj;

    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
    IWorkbenchPage page = window.getActivePage();

    long idForPerson = selectedPerson.getId();
    EditorInput editor = new EditorInput(idForPerson);
    try {
      page.openEditor(editor, InputEditor.ID);
      myString.setString(selectedPerson);
      log.info(myString.getString());
    } catch (PartInitException e) {
      e.printStackTrace();
    }
    log.info("ok");
    return null;
  }
}
