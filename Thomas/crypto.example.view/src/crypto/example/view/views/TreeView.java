package crypto.example.view.views;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

import crypto.api.Directory;
import crypto.api.Person;
import crypto.example.view.Activator;
import crypto.example.view.jface.provider.TreeViewerLabelProvider;

public class TreeView extends ViewPart {
  @SuppressWarnings("unused")
  private static Logger log = Logger.getLogger(TreeView.class);

  public static final String ID = "crypto.example.view.tree";

  @Override
  public void createPartControl(Composite parent) {
    Directory directoryService = Activator.getDirectoryService();
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(2, false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));

    int operations = DND.DROP_MOVE | DND.DROP_COPY;

    CustomTransferTypes[] types = new CustomTransferTypes[] {CustomTransferTypes.getInstance()};

    TreeViewer viewer = createViewer(composite);
    List<Person> newList = new ArrayList<>();

    TreeViewerDropAdapter dropAdapter = new TreeViewerDropAdapter(viewer, newList);

    viewer.setInput(newList);

    viewer.addDropSupport(operations, types, dropAdapter);

    ListDragAdapter dragAdapter = new ListDragAdapter(viewer, newList);

    viewer.addDragSupport(operations, types, dragAdapter);

    Control tree = viewer.getControl();
    MenuManager menuManager = new MenuManager();
    Menu menu = menuManager.createContextMenu(tree);
    // set the menu on the SWT widget
    tree.setMenu(menu);
    // register the menu with the framework
    getSite().registerContextMenu(menuManager, viewer);
    // make the viewer selection available
    getSite().setSelectionProvider(viewer);

    directoryService.editPersonListener(
        (id, person) -> {
          viewer.refresh();
        });
    directoryService.addRemovePersonListener(
        (id, person) -> {
          newList.remove(person);
          viewer.refresh();
        });
  }

  protected TreeViewer createViewer(Composite parent) {
    TreeViewer viewerObjs = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5);
    viewerObjs.getControl().setLayoutData(gd);
    Directory directoryService = Activator.getDirectoryService();

    viewerObjs.setContentProvider(
        new ITreeContentProvider() {

          @Override
          public void dispose() {}

          @Override
          public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

          @SuppressWarnings("unchecked")
          @Override
          public Object[] getElements(Object inputElement) {
            if (inputElement instanceof List) {
              return ((List<Person>) inputElement).toArray();
            }
            return null;
          }

          @Override
          public Object[] getChildren(Object parentElement) {

            if (parentElement instanceof Person) {
              Person person = (Person) parentElement;
              Object[] children = new Object[3];
              PersonReference[] friendArray =
                  person
                      .getFriends()
                      .stream()
                      .map(p -> new PersonReference(p))
                      .toArray(PersonReference[]::new);
              children[0] = "Age: " + person.getAge();
              children[1] = "Address: " + person.getAddress();
              children[2] = friendArray;
              return children;
            }
            if (parentElement instanceof PersonReference[]) {
              return (PersonReference[]) parentElement;
            }
            return new Object[0];
          }

          @Override
          public Object getParent(Object element) {
            return null;
          }

          @Override
          public boolean hasChildren(Object element) {
            if (element instanceof Person) {
              return true;
            }
            if (element instanceof PersonReference[] && ((PersonReference[]) element).length > 0) {
              return true;
            }
            return false;
          }
        });

    viewerObjs.setLabelProvider(new TreeViewerLabelProvider(directoryService));

    return viewerObjs;
  }

  @Override
  public void setFocus() {}
}
