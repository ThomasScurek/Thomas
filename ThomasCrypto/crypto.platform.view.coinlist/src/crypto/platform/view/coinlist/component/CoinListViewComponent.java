package crypto.platform.view.coinlist.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPartSite;

import crypto.model.coin.Coin;
import crypto.platform.ui.api.component.IComponent;
import crypto.platform.view.coinlist.Activator;
import crypto.platform.view.coinlist.controller.CoinListViewComponentController;
import crypto.platform.view.coinlist.jface.providers.CoinListContentProvider;
import crypto.platform.view.coinlist.jface.providers.CoinListLabelProvider;
import crypto.platform.view.coinlist.views.BuyCoin;
import crypto.platform.view.coinlist.views.CoinSearch;
import crypto.services.coin.CoinService;
import crypto.services.price.PriceService;

public class CoinListViewComponent implements IComponent {
  private static Logger log = Logger.getLogger(CoinListViewComponent.class);
  CoinService directoryService = Activator.getCoinService();
  PriceService priceService = Activator.getPriceService();
  private List<Coin> loadedList = directoryService.getLoadedCoinList();
  private List<Coin> userList;

  private final IWorkbenchPartSite iWorkbenchPartSite;

  public CoinListViewComponent(IWorkbenchPartSite iWorkbenchPartSite) {

    this.iWorkbenchPartSite = iWorkbenchPartSite;
  }

  @Override
  public CoinListViewComponentController create(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(1, false));
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    TreeViewer treeView = new TreeViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
    treeView.getControl().setLayoutData(gd);

    treeView.setContentProvider(new CoinListContentProvider());
    treeView.setLabelProvider(new CoinListLabelProvider());

    Button addButton = new Button(composite, SWT.BORDER);
    addButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
    addButton.setText("Add Coin");
    
    Button purchaseButton = new Button(composite, SWT.BORDER);
    purchaseButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
    purchaseButton.setText("Buy Coin");

    userList = new ArrayList<>();

    addButton.addListener(
        SWT.Selection,
        e -> {
          CoinSearch searchCoin = new CoinSearch(Display.getCurrent().getActiveShell());
          searchCoin.create();

          if (searchCoin.open() == Window.OK) {
            //            PriceBar myPrice = new PriceBar();
            String coinName = searchCoin.getCoinName();
            Coin userCoin =
                loadedList
                    .stream()
                    .filter(s -> s.getCoinSymbol().equals(coinName))
                    .findFirst()
                    .get();
            log.info(userCoin.getCoinSymbol());
            userList.add(userCoin);
            directoryService.addAllUserCoinList(userList);
            //add subscription
            //add consumer for Coin
            
            userList.clear();
            //            log.info(myPrice.getCoinOpen());
          }
          treeView.refresh();
        });
    
    purchaseButton.addListener(
        SWT.Selection,
        e -> {
          BuyCoin buyCoin = new BuyCoin(Display.getCurrent().getActiveShell());
          buyCoin.create();

          if (buyCoin.open() == Window.OK) {
            //            PriceBar myPrice = new PriceBar();
            String coinName = buyCoin.getCoinName();
            Coin userCoin =
                loadedList
                    .stream()
                    .filter(s -> s.getCoinSymbol().equals(coinName))
                    .findFirst()
                    .get();
            log.info(userCoin.getCoinSymbol());
            userList.add(userCoin);
            directoryService.addAllUserCoinList(userList);
            userList.clear();
            //            log.info(myPrice.getCoinOpen());
          }
          treeView.refresh();
        });
    treeView.refresh();

    Control tree = treeView.getControl();
    MenuManager menuManager = new MenuManager();
    Menu menu = menuManager.createContextMenu(tree);
    // set the menu on the SWT widget
    tree.setMenu(menu);
    // register the menu with the framework
    iWorkbenchPartSite.registerContextMenu(menuManager, treeView);
    // make the viewer selection available
    iWorkbenchPartSite.setSelectionProvider(treeView);
    return new CoinListViewComponentController(composite, treeView);
  }
  
  public void priceUpdate(String response){
    //parse info
    //figure out what coin you have
    //find coin in the list
    //find price in coin
    //update price
  }
}
