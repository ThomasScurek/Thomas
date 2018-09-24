package crypto.platform.view.coinlist.jface.providers;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import crypto.model.coin.Coin;
import crypto.model.pricebar.PriceBar;
import crypto.platform.view.coinlist.Activator;
import crypto.services.coin.CoinService;
import crypto.services.price.PriceService;

public class CoinListContentProvider implements ITreeContentProvider {
  CoinService directoryService = Activator.getCoinService();
  PriceService priceService = Activator.getPriceService();

  @Override
  public void dispose() {}

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

  @Override
  public Object[] getElements(Object inputElement) {
    if (inputElement instanceof List) {
      return ((List<?>) inputElement).toArray();
    }
    return null;
  }

  @Override
  public Object[] getChildren(Object parentElement) {

    if (parentElement instanceof Coin) {
      Coin myCoin = (Coin) parentElement;
      List<PriceBar> myPrice = priceService.getHistoricalData(myCoin);
      Object[] children = new Object[5];
      children[0] = "Symbol: " + myCoin.getCoinSymbol();
      children[1] = "Name: " + myCoin.getCoinName();
      children[2] = "Id: " + myCoin.getCoinId();
      children[3] = "Current Price: $" + priceService.getCurrentPrice(myCoin);
      children[4] = "Open Price: $" + myPrice.get(myPrice.size() - 1).getCoinOpen();
      return children;
    }
    return null;
  }

  /*public void Color(Coin coin) {
    PriceBar myPrice = new PriceBar();
    Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
    Color black = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    Color green = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);

    if (myPrice.getCoinOpen() > priceService.getCurrentPrice(coin)) {
      priceService.getCurrentPrice(coin).toString().setForeground(red);
    } else {
      priceService.getCurrentPrice(coin).toString().setForeground(black);
    }
    if (myPrice.getCoinOpen() < priceService.getCurrentPrice(coin)) {
      priceService.getCurrentPrice(coin).toString().setForeground(green);
    } else {
      priceService.getCurrentPrice(coin).toString().setForeground(black);
    }
  }*/

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof Coin) {
      return true;
    }
    return false;
  }
}
