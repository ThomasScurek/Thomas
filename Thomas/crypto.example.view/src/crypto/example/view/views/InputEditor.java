package crypto.example.view.views;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import crypto.api.Directory;
import crypto.api.Person;
import crypto.example.view.Activator;
import crypto.example.view.jface.provider.TreeViewerLabelProvider;

public class InputEditor extends EditorPart {

  public static final String ID = "crypto.example.view.personeditor";

  private static final Directory directoryService = Activator.getDirectoryService();
  private static Logger log = Logger.getLogger(InputEditor.class);

  private EditorInput editorInput;
  private int myFriendsId;
  private long id;

  private StringConversion myString = new StringConversion();

  private Person newPerson;

  private Text firstName, lastName, age, address;
  private ComboViewer friendList;
  public TreeViewer friendView;

  private List<Person> myFriends;
  private List<Person> possibleFriends;

  private BiConsumer<Long, Person> refreshFriendsHandler;
  private BiConsumer<Long, Person> closeEditorHandler;

  // Will be called before createPartControl
  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    if (!(input instanceof EditorInput)) {
      throw new RuntimeException("Wrong input");
    }

    this.editorInput = (EditorInput) input;
    setSite(site);
    setInput(input);
    // editorInput.setId(directoryService.idForPerson(newPerson));
    id = editorInput.getId();
    if (id == -1) {
      newPerson = new Person();
      directoryService.addRemovePersonListener(
          (id, person) -> {
            myString.setString(person);
            log.info(
                "removing "
                    + myString.getString()
                    + " from "
                    + myString.getString()
                    + "'s friends");
            //            try {
            //              // friendView.setInput(myFriends);
            //
            //              // refreshCombo();
            //            } catch (SWTException e) { // exception if editor is closed
            //              // e.printStackTrace();
            //            }
            myFriends.remove(person);
          });
    } else {
      newPerson = directoryService.personById(id);
    }
    if (newPerson.getFirstName() != null
        && newPerson.getLastName() != null
        && newPerson.getAge() != null
        && newPerson.getAddress() != null) {
      setPartName(getEditorName());
    } else {
      setPartName("Person");
    }

    addListeners();
  }

  private void addListeners() {
    closeEditorHandler =
        (id, person) -> {
          log.info("comparing " + editorInput.getId() + " to " + id.longValue());
          if (editorInput.getId()
              == id.longValue()) { // close editor if id is the same as removed person
            StringConversion myString = new StringConversion();
            log.info("closing editor for " + myString.getString());
            IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            IWorkbenchPage page = workbenchWindow.getActivePage();
            page.closeEditor(this, false);
          }
        };

    refreshFriendsHandler =
        (id, person) -> {
          if (!friendView.getTree().isDisposed()) {
            friendView.refresh();
          }
          if (!friendView.getTree().isDisposed()) {
            friendList.refresh();
          }
        };
    directoryService.addRemovePersonListener(closeEditorHandler);
    directoryService.addRemovePersonListener(refreshFriendsHandler);
  }

  private String getEditorName() {
    return (newPerson == null)
        ? ""
        : new StringBuilder()
            .append(newPerson.getFirstName())
            .append(" ")
            .append(newPerson.getLastName())
            .toString();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void createPartControl(Composite parent) {

    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    parent.setLayout(layout);

    new Label(parent, SWT.NONE).setText("First Name");
    firstName = new Text(parent, SWT.BORDER);
    firstName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    firstName.addModifyListener(e -> firePropertyChange(PROP_DIRTY));

    new Label(parent, SWT.NONE).setText("Last Name");
    lastName = new Text(parent, SWT.BORDER);
    lastName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    lastName.addModifyListener(e -> firePropertyChange(PROP_DIRTY));

    new Label(parent, SWT.NONE).setText("Age");
    age = new Text(parent, SWT.BORDER);
    age.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    age.addModifyListener(e -> firePropertyChange(PROP_DIRTY));

    new Label(parent, SWT.NONE).setText("Address");
    address = new Text(parent, SWT.BORDER);
    address.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    address.addModifyListener(e -> firePropertyChange(PROP_DIRTY));

    myFriends = newPerson.getFriends();
    if (myFriends == null) myFriends = new ArrayList();
    GridLayout gd = new GridLayout(2, false);
    parent.setLayout(gd);

    Label label = new Label(parent, SWT.NONE);
    label.setText("Select a person:");
    friendList = new ComboViewer(parent, SWT.READ_ONLY);
    friendList.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    friendList.setContentProvider(ArrayContentProvider.getInstance());
    friendList.setLabelProvider(
        new LabelProvider() {
          @Override
          public String getText(Object element) {
            Person p = (Person) element;
            myString.setString(p);
            return myString.getString();
          }
        });
    firePropertyChange(PROP_DIRTY);

    friendView = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    GridData grd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5);
    friendView.getControl().setLayoutData(grd);

    friendView.setContentProvider(
        new ITreeContentProvider() {

          @Override
          public void dispose() {}

          @Override
          public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

          @Override
          public Object[] getElements(Object inputElement) {
            if (inputElement instanceof List) {
              return ((List<Person>) inputElement).toArray();
            }
            return null;
          }

          @Override
          public Object[] getChildren(Object parentElement) {
            return null;
          }

          @Override
          public Object getParent(Object element) {
            return null;
          }

          @Override
          public boolean hasChildren(Object element) {
            return false;
          }
        });

    friendView.setLabelProvider(new TreeViewerLabelProvider(directoryService));
    possibleFriends = new ArrayList();
    friendView.setInput(myFriends);
    refreshCombo();
    friendView.refresh();

    Button addFriend = new Button(parent, SWT.BORDER);
    addFriend.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
    addFriend.setText("Add Friend");

    addFriend.addListener(
        SWT.Selection,
        e -> {
          Person friend =
              (Person) ((StructuredSelection) friendList.getSelection()).getFirstElement();
          if (friend != null && !myFriends.contains(friend)) {
            myFriends.add(friend);
            myString.setString(friend);
            log.info(myFriendsId);
            log.info(myString.getString());
            refreshCombo();
            friendView.setInput(myFriends);
            friendView.refresh();
            firePropertyChange(PROP_DIRTY);
          }
          friendView.setInput(myFriends);
          refreshCombo();
          friendView.refresh();
        });

    if (newPerson != null
        && newPerson.getFirstName() != null
        && newPerson.getLastName() != null
        && newPerson.getAge() != null
        && newPerson.getAddress() != null
        && newPerson.getFriends() != null) {
      String firstNameString = newPerson.getFirstName();
      String lastNameString = newPerson.getLastName();
      Integer myAge = newPerson.getAge();
      String myAddress = newPerson.getAddress();
      myFriends = newPerson.getFriends();
      firstName.setText(firstNameString);
      lastName.setText(lastNameString);
      age.setText(String.valueOf(myAge));
      address.setText(myAddress);
    }

    friendView.setInput(myFriends);
    friendView.refresh();
  }

  public void refreshCombo() {
    possibleFriends.clear();
    possibleFriends = directoryService.getAll();
    possibleFriends.removeAll(myFriends);
    possibleFriends.remove(newPerson);
    friendList.setInput(possibleFriends);
    friendList.refresh();
  }

  @Override
  public void doSave(IProgressMonitor monitor) {
    Person person;
    if (!newPerson.equals(directoryService.personById(id))) {
      if (testPattern() == true) {
        person =
            new Person(
                firstName.getText(),
                lastName.getText(),
                Integer.parseInt(age.getText()),
                address.getText(),
                myFriends);
        long newPersonId = directoryService.addPerson(person);
        newPerson = person;
        id = newPersonId;
        editorInput.setId(newPersonId);
        refreshCombo();
      } else {
        setColor();
      }
    } else if (newPerson.equals(directoryService.personById(id)) && testPattern() == true) {
      newPerson =
          directoryService.editPerson(
              id,
              firstName.getText(),
              lastName.getText(),
              Integer.parseInt(age.getText()),
              address.getText(),
              myFriends);
      setColor();
      refreshCombo();
    }
    firePropertyChange(PROP_DIRTY);
    setPartName(getEditorName());
    setColor();
    refreshCombo();
  }

  @Override
  public void doSaveAs() {}

  @Override
  public boolean isDirty() {
    if (firstName.getText().equals("")
        || lastName.getText().equals("")
        || age.getText().equals("")
        || address.getText().equals("")
        || myFriends == null) {
      refreshCombo();
      return false;
    }
    if (!firstName.getText().equals(newPerson.getFirstName())
        || !lastName.getText().equals(newPerson.getLastName())
        || Integer.parseInt(age.getText()) != newPerson.getAge()
        || !address.getText().equals(newPerson.getAddress())
        || !(newPerson.getFriends().equals(myFriends))) {
      refreshCombo();
      return true;
    }
    return false;
  }

  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  public boolean testPattern() {
    if (Pattern.matches("[a-zA-Z]+", firstName.getText())
        && Pattern.matches("[a-zA-Z]+", lastName.getText())
        && Pattern.matches("[0-9]+", age.getText())
        && Pattern.matches("[a-zA-Z0-9\\s]+", address.getText())) {
      return true;
    }
    return false;
  }

  public void setColor() {
    Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
    Color black = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    if (!Pattern.matches("[a-zA-Z]+", firstName.getText())) {
      firstName.setForeground(red);
    } else {
      firstName.setForeground(black);
    }
    if (!Pattern.matches("[a-zA-Z]+", lastName.getText())) {
      lastName.setForeground(red);
    } else {
      lastName.setForeground(black);
    }
    if (!Pattern.matches("[0-9]+", age.getText())) {
      age.setForeground(red);
    } else {
      age.setForeground(black);
    }
    if (!Pattern.matches("[a-zA-Z0-9\\s]+", address.getText())) {
      address.setForeground(red);
    } else {
      address.setForeground(black);
    }
  }

  @Override
  public void setFocus() {
    firstName.setFocus();
  }

  @Override
  public void dispose() {

    directoryService.removeRemovePersonListener(closeEditorHandler);
    directoryService.removeRemovePersonListener(refreshFriendsHandler);

    super.dispose();
  }
}
