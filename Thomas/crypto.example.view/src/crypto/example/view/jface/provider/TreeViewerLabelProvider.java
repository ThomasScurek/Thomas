package crypto.example.view.jface.provider;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import crypto.api.Directory;
import crypto.api.Person;
import crypto.example.view.Activator;
import crypto.example.view.views.PersonReference;
import crypto.example.view.views.StringConversion;

public class TreeViewerLabelProvider extends LabelProvider
    implements IColorProvider, ILabelProvider {
  @SuppressWarnings("unused")
  private static Logger log = Logger.getLogger(TreeViewerLabelProvider.class);

  private Directory directoryService;
  private StringConversion myString = new StringConversion();

  public TreeViewerLabelProvider(Directory directoryService) {
    this.directoryService = directoryService;
  }

  @Override
  public String getText(Object element) {
    if (element instanceof Person) {
      Person p = (Person) element;
      myString.setString(p);
      return myString.getString();
    }
    if (element instanceof PersonReference[]) {
      return "Friends";
    }
    if (element instanceof PersonReference) {
      PersonReference friendRef = (PersonReference) element;
      directoryService = Activator.getDirectoryService();
      Person friend = directoryService.personById(friendRef.getId());
      myString.setString(friend);
      return myString.getString();
    }
    return element.toString();
  }

  // @Override
  public Color getForeground(Object element) {
    if (element instanceof Person) {
      if (element.toString().length() % 2 == 1) {
        return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
      } else if (element.toString().length() % 2 == 0) {
        return Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
      }
    }
    return null;
  }

  @Override
  public Color getBackground(Object element) {
    // TODO Auto-generated method stub
    return null;
  }
}
