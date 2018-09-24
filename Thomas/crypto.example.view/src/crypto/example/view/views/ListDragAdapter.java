package crypto.example.view.views;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

import crypto.api.Person;

public class ListDragAdapter extends DragSourceAdapter {
  private final Viewer viewer;
  private List<Person> thisList;
  private static Logger log = Logger.getLogger(ListDragAdapter.class);
  private Person thing;

  public ListDragAdapter(Viewer viewer, List<Person> thisList) {
    this.viewer = viewer;
    this.thisList = thisList;
  }

  public void dragStart(DragSourceEvent event) {

    ISelection selection = viewer.getSelection();

    IStructuredSelection castedSelection = (IStructuredSelection) selection;

    Person element = (Person) castedSelection.getFirstElement();

    thing = element;
    event.doit = (thing != null);
  }

  public void dragSetData(DragSourceEvent event) {
    if (CustomTransferTypes.getInstance().isSupportedType(event.dataType)) {
      ISelection selection = viewer.getSelection();
      //    log.info("this works");

      log.info("hello");
      IStructuredSelection castedSelection = (IStructuredSelection) selection;

      Person element = (Person) castedSelection.getFirstElement();

      event.data = element;
    }
  }

  public void dragFinished(DragSourceEvent event) {
    @SuppressWarnings("unused")
    int index = -1;
    for (int i = 0; i < thisList.size(); i++) {
      if (thisList.get(i).equals(thing)) {
        index = i;
      }
    }
    if (event.detail == DND.DROP_MOVE) {
      thisList.remove(thing);
    }
    viewer.refresh();
  }
}
