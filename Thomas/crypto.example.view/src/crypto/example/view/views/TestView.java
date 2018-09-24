package crypto.example.view.views;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import crypto.api.Directory;
import crypto.api.Person;
import crypto.example.view.Activator;

public class TestView extends ViewPart {

  private static Logger log = Logger.getLogger(TestView.class);
  public static final String ID = "crypto.example.view";
  private StringConversion myString = new StringConversion();

  class ViewLabelProvider extends LabelProvider {

    public String getColumText(Object obj, int index) {
      return getText(obj);
    }

    public Image getColumnImage(Object obj, int index) {
      return getImage(obj);
    }

    public Image getImage(Object obj) {
      return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
    }
  }

  protected ListViewer createViewer(Composite parent) {
    ListViewer viewerObjs = new ListViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5);
    viewerObjs.getControl().setLayoutData(gd);
    viewerObjs.setContentProvider(ArrayContentProvider.getInstance());
    viewerObjs.setLabelProvider(
        new LabelProvider() {

          @Override
          public String getText(Object element) {
            myString.setString((Person) element);
            return myString.getString();
          }
        });
    
    return viewerObjs;
  }

  @Override
  public void createPartControl(Composite parent) {

    log.info("View created!");
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(2, false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
    Label label = new Label(composite, SWT.NONE);
    label.setText("List");
    label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    Button button = new Button(composite, SWT.BORDER);
    button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
    button.setText("Add");

    Directory directoryService = Activator.getDirectoryService();
    List<Person> personList = directoryService.getAll();
    ListViewer viewer = createViewer(composite);
    int operations = DND.DROP_MOVE | DND.DROP_COPY;
    
    CustomTransferTypes[] types = new CustomTransferTypes[] {CustomTransferTypes.getInstance()};

    ListDragAdapter dragAdapter = new ListDragAdapter(viewer, personList);
    
    viewer.addDragSupport(operations, types, dragAdapter);

    directoryService.addNewPersonListener(
        (id, person) -> {
          personList.add(person);
          myString.setString(person);
          log.info(myString.getString());
          viewer.refresh();
        });
    directoryService.editPersonListener(
       (id, person) -> {
          viewer.refresh();
        });
    directoryService.addRemovePersonListener(
      (id, person) -> {
          personList.remove(person);
          viewer.refresh();
        });
    button.addListener(
        SWT.Selection,
        e -> {
          EditorInput editorInput = new EditorInput(-1);
          try {
            getViewSite().getPage().openEditor(editorInput, InputEditor.ID);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
          /*PopUp input = new PopUp(Display.getCurrent().getActiveShell());
                    if (input.open() == Window.OK) {

                      String firstName = input.getFirstName();
                      String lastName = input.getLastName();
                      String age = input.getAge();

                      Person newPerson = new Person(firstName, lastName, age);

                      personList.add(newPerson);
                      directoryService.addPerson(newPerson);
                      personList.clear();
                      personList.addAll(directoryService.getAll());
                      log.info(newPerson.toString());
                    }
                    viewer.refresh();*/
        });
    TreeViewerDropAdapter dropAdapter = new TreeViewerDropAdapter(viewer, personList);

    viewer.setInput(personList);

    viewer.addDropSupport(operations, types, dropAdapter);
    
    viewer.setInput(personList);

    MenuManager menuManager = new MenuManager();
    Menu menu = menuManager.createContextMenu(viewer.getList());
    // set the menu on the SWT widget
    viewer.getList().setMenu(menu);
    // register the menu with the framework
    getSite().registerContextMenu(menuManager, viewer);
    // make the viewer selection available
    getSite().setSelectionProvider(viewer);
  }

  @Override
  public void setFocus() {}
}
