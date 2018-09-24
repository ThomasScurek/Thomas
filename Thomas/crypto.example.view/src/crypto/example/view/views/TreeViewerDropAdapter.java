package crypto.example.view.views;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import crypto.api.Person;

public class TreeViewerDropAdapter extends ViewerDropAdapter {

  private final Viewer viewer;
  private List<Person> newList;
  private static Logger log = Logger.getLogger(TreeViewerDropAdapter.class);

  protected TreeViewerDropAdapter(Viewer viewer, List<Person> newList) {
    super(viewer);
    this.viewer = viewer;
    this.newList = newList;
  }

  @Override
  public boolean performDrop(Object data) {
    int location = getCurrentLocation();
    log.info(location);
    int index = newList.indexOf(getCurrentTarget());

     Person newPerson = (Person) data;
    switch (location) {
      case ViewerDropAdapter.LOCATION_BEFORE:
        log.info("before");
        newList.add(index, newPerson);
        break;
      case ViewerDropAdapter.LOCATION_ON:
      case ViewerDropAdapter.LOCATION_AFTER:
        log.info("after");
        newList.add(index + 1, newPerson);
        break;
      case ViewerDropAdapter.LOCATION_NONE:
        log.info("none");
        newList.add(newPerson);
        break;
      default:
        log.info("default");
        break;
    }
    viewer.refresh();

    return true;
  }

  @Override
  public boolean validateDrop(Object target, int operation, TransferData transferType) {
    return true;
  }
}
