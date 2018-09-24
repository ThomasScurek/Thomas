package crypto.platform.view.coinlist.views;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import crypto.model.coin.Coin;
import crypto.platform.view.coinlist.Activator;
import crypto.platform.view.coinlist.component.CoinListViewComponent;
import crypto.platform.view.coinlist.controller.CoinListViewComponentController;
import crypto.services.coin.CoinService;

public class CoinListView extends ViewPart {
  public static final String ID = "crypto.platform.view.coinlist.views.CoinListView";
  CoinService directoryService = Activator.getCoinService();
  private List<Coin> userCoinList = directoryService.getUserCoinList();

  public void createPartControl(Composite parent) {
    CoinListViewComponentController coinListController =
        new CoinListViewComponent(getSite()).create(parent);
    coinListController.setInput(userCoinList);
    coinListController.refresh();
    
    /*
    directoryService.addRemovePersonListener(
        (id, coin) -> {
          newList.remove(coin);
          viewer.refresh();
        });*/
    //	  }

  }

  public void setFocus() {}
}
