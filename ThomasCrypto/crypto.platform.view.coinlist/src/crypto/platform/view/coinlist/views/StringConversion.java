package crypto.platform.view.coinlist.views;

import crypto.model.coin.Coin;
import crypto.model.pricebar.PriceBar;

public class StringConversion {
	private String myCoin;
	private String coinPrice;
	
	public void setString(Coin c){
		if (c != null){
			myCoin = c.getCoinSymbol();
		}
	}
	
	public void setPriceString(PriceBar price){
	  if(price != null){
	    coinPrice = "$" + price.getCoinOpen().toString();
	  }
	}
	
	public String getString(){
		return myCoin;
	}
	
	public String getPriceString(){
	  return coinPrice;
	}
}
