package crypto.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import crypto.api.cryptocompare.api.requester.ApiRequester;
import crypto.api.cryptocompare.api.requester.PriceParser;
import crypto.api.cryptocompare.api.requester.UrlBuilder;
import crypto.model.coin.Coin;
import crypto.services.coin.CoinService;

public class CoinServiceImpl implements CoinService {
  private final List<Coin> userCoinList = new ArrayList<>();
  private static Logger log = Logger.getLogger(CoinServiceImpl.class);
  private List<Coin> myCoinList;
  private final List<BiConsumer<Long, Coin>> newCoinEventListeners;
  private final List<BiConsumer<Long, Coin>> removeCoinEventListeners;

  public CoinServiceImpl() {
    newCoinEventListeners = new ArrayList<>();
    removeCoinEventListeners = new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public List<Coin> fetchLoadedCoinList() {
    ApiRequester request = new ApiRequester();
    myCoinList = new ArrayList<>();
    if (myCoinList.isEmpty()) {
      try {
        UrlBuilder myUrl = new UrlBuilder();
        String coinTester = request.sendRequest(myUrl.getCoinUrl());
        PriceParser parse = new PriceParser();
        Map<String, Object> Json = parse.convertJson(coinTester);
        Map<String, Object> Data = (Map<String, Object>) Json.get("Data");
        Set<String> keys = Data.keySet();
        List<Coin> coinList =
            keys.stream()
                .map(
                    coin -> {
                      Coin myCoin = new Coin();
                      Map<String, Object> coinInfo = (Map<String, Object>) Data.get(coin);
                      myCoin.setCoinName(coinInfo.get("CoinName").toString());
                      myCoin.setCoinSymbol(coinInfo.get("Symbol").toString());
                      myCoin.setCoinId(Long.parseLong(coinInfo.get("Id").toString()));
                      return myCoin;
                    })
                .collect(Collectors.toList());
        return coinList;
//        return myCoinList;
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return Collections.emptyList();
  }
  
  @Override
  public List<Coin> getLoadedCoinList(){
    if (myCoinList == null || myCoinList.isEmpty())
      myCoinList = fetchLoadedCoinList();
    return myCoinList;
  }

  @Override
  public List<Coin> getUserCoinList() {

    return userCoinList;
  }

  @Override
  public Long addCoin(Coin coin) {
    long nextId = coin.getId();
    userCoinList.add(coin);
    newCoinEventListeners.forEach(listener -> listener.accept(nextId, coin));
    return nextId;
  }

  @Override
  public void removeCoin(Coin coin) {
    // TODO Auto-generated method stub
  }

  @Override
  public void addAllUserCoinList(List<Coin> list) {
    userCoinList.forEach(coin-> addCoin(coin));
  }

  @Override
  public void addRemoveCoinListeners(BiConsumer<Long, Coin> coinListener) {
    removeCoinEventListeners.add(coinListener);
  }

  @Override
  public void removeRemoveCoinListeners(BiConsumer<Long, Coin> coinListener) {
    removeCoinEventListeners.remove(coinListener);
  }

  @Override
  public void addAddCoinListeners(BiConsumer<Long, Coin> coinListener) {
    newCoinEventListeners.add(coinListener);
  }

  @Override
  public void removeAddCoinListeners(BiConsumer<Long, Coin> coinListener) {
    newCoinEventListeners.remove(coinListener);
  }
}
