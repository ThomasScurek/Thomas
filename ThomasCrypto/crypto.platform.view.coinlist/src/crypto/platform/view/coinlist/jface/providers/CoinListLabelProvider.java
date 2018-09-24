package crypto.platform.view.coinlist.jface.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import crypto.model.coin.Coin;
import crypto.platform.view.coinlist.views.StringConversion;

public class CoinListLabelProvider implements ILabelProvider {
  private StringConversion myString = new StringConversion();
	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof Coin){
			Coin c = (Coin) element;
			myString.setString(c);
			return myString.getString();
		}
		return element.toString();
	}

}
