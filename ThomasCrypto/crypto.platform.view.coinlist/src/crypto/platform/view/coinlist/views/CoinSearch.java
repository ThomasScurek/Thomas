package crypto.platform.view.coinlist.views;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import crypto.model.coin.Coin;
import crypto.platform.view.coinlist.Activator;
import crypto.services.coin.CoinService;

public class CoinSearch extends TitleAreaDialog {
  CoinService directoryService = Activator.getCoinService();
  private static Logger log = Logger.getLogger(CoinSearch.class);
  private String coinName, coinSymbol;
  private Long coinId;
  private static Long holder;
  private Text searchText;
  private List<Coin> loadedList = directoryService.getLoadedCoinList();

  public CoinSearch(Shell parentShell) {
    this(parentShell, "", "", holder);
  }

  public CoinSearch(Shell shell, String name, String symbol, Long id) {
    super(shell);
    coinId = id;
    coinSymbol = symbol;
    coinName = name;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    log.info("Create dialog");

    CoinSearch myCoinSearch = new CoinSearch(Display.getCurrent().getActiveShell());

    if (coinName.equals(null) || coinName.equals("")) {
      log.info("WHY");
    }

    Control container = super.createDialogArea(parent);

    Composite composite = new Composite((Composite) container, SWT.NONE);
    composite.setLayout(new GridLayout(2, false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    Label promptLabel = new Label(composite, SWT.NONE);
    promptLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    promptLabel.setText("Search Coin:");

    searchText = new Text(composite, SWT.BORDER);
    searchText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    searchText.setText(coinName);

    String[] symbolArray = loadedList.stream().map(s -> s.getCoinSymbol()).toArray(String[]::new);

    new AutoCompleteField(searchText, new TextContentAdapter(), symbolArray);

    ModifyListener textListener =
        e -> getButton(IDialogConstants.OK_ID).setEnabled(BasicValidateAction());

    searchText.addModifyListener(textListener);

    return composite;
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    super.createButtonsForButtonBar(parent);
    getButton(IDialogConstants.OK_ID).setEnabled(false);
  }

  private boolean BasicValidateAction() {
    if ((!loadedList.isEmpty())
        && loadedList
            .stream()
            .filter(s -> s.getCoinSymbol().equals(searchText.getText()))
            .findFirst()
            .isPresent()) {
      return true;
    }
    return false;
  }

  @Override
  protected void okPressed() {
    coinName = searchText.getText();

    super.okPressed();
  }

  public String getCoinName() {
    return coinName;
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
