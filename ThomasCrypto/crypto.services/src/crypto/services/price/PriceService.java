package crypto.services.price;

import java.util.List;
import java.util.function.BiConsumer;

import crypto.model.coin.Coin;
import crypto.model.pricebar.PriceBar;

public interface PriceService {
  public Double getCurrentPrice(Coin coin);
  public List<PriceBar> getHistoricalData(Coin coin);
  public void addAllUserPriceList(List<PriceBar> list);
  
  //for model updates
  public void addNewPriceConsumer(BiConsumer<String, Double> priceConsumer);
  
  //Use this for ui updates
  public void addPostPriceConsumer(BiConsumer<String, Double> priceConsumer);
  
  public void addSubscription(String coinSymbol);
}
