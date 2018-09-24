package crypto.example.view.views;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PopUp extends TitleAreaDialog {
  private static Logger log = Logger.getLogger(PopUp.class);
  private String firstInput;
  private String lastInput;
  private Integer ageInput;
  private static int holder;
  private final String defString;
  private final String thisString;
  private final Integer thatInt;
  private Text firstText, lastText, ageText;

  public PopUp(Shell parentShell) {
    this(parentShell, "", "", holder );
  }

  public PopUp(Shell shell, String first, String last, Integer age) {
    super(shell);
    defString = first;
    thisString = last;
    thatInt = age;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    log.info("Create dialog");

    // PopUp myPopUp = new PopUp(Display.getCurrent().getActiveShell(), getData());

    if (defString.equals(null) || defString.equals("")) {
      log.info("WHY");
    }

    Control container = super.createDialogArea(parent);

    Composite composite = new Composite((Composite) container, SWT.NONE);
    composite.setLayout(new GridLayout(2, false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 3));

    Label firstLabel = new Label(composite, SWT.NONE);
    firstLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
    firstLabel.setText("First Name:");

    Label lastLabel = new Label(composite, SWT.NONE);
    lastLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
    lastLabel.setText("Last Name:");

    Label ageLabel = new Label(composite, SWT.NONE);
    ageLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1));
    ageLabel.setText("Age:");

    firstText = new Text(composite, SWT.BORDER);
    firstText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
    firstText.setText(defString);

    lastText = new Text(composite, SWT.BORDER);
    lastText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 2));
    lastText.setText(thisString);

    ageText = new Text(composite, SWT.BORDER);
    ageText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 2));
    ageText.setText(""+thatInt);

    ModifyListener textListener =
        e -> getButton(IDialogConstants.OK_ID).setEnabled(BasicValidateAction());

    firstText.addModifyListener(textListener);
    lastText.addModifyListener(textListener);
    ageText.addModifyListener(textListener);

    return composite;
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    super.createButtonsForButtonBar(parent);
    getButton(IDialogConstants.OK_ID).setEnabled(false);
  }

  private boolean BasicValidateAction() {
    if (Pattern.matches("[a-zA-Z0-9]+", firstText.getText())
        && Pattern.matches("[a-zA-Z0-9]+", lastText.getText())
        && Pattern.matches("[a-zA-Z0-9]+", ageText.getText())) {
      return true;
    }
    return false;
  }

  @Override
  protected void okPressed() {
    firstInput = firstText.getText();
    lastInput = lastText.getText();
    ageInput = Integer.valueOf(ageText.getText());

    super.okPressed();
  }

  public String getFirstName() {
    return firstInput;
  }

  public String getLastName() {
    return lastInput;
  }

  public int getAge() {
    return ageInput;
  }

  /*public void setTextBox(){
  	if(!input.equals(defString) && defString!= null){
  		input = defString;
  		textBox.setText(input);
  	}
  }

  public Text getTextBox(){
  	return textBox;
  }*/

}
