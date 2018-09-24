package crypto.services.coin;

import java.util.List;
import java.util.function.BiConsumer;

import crypto.model.coin.Coin;

public interface CoinService {
  public List<Coin> getLoadedCoinList();

  public List<Coin> getUserCoinList();

  public Long addCoin(Coin coin);

  public void removeCoin(Coin coin);

  public void addRemoveCoinListeners(BiConsumer<Long, Coin> consumer);

  public void removeRemoveCoinListeners(BiConsumer<Long, Coin> consumer);

  public void addAddCoinListeners(BiConsumer<Long, Coin> consumer);

  public void removeAddCoinListeners(BiConsumer<Long, Coin> consumer);

  public void addAllUserCoinList(List<Coin> list);
  
}
