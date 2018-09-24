package crypto.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import crypto.api.cryptocompare.api.requester.ApiRequester;
import crypto.api.cryptocompare.api.requester.CryptoParser;
import crypto.api.cryptocompare.api.requester.UrlBuilder;
import crypto.api.cryptocompare.socket.SocketTest;
import crypto.model.coin.Coin;
import crypto.model.pricebar.PriceBar;
import crypto.services.price.PriceService;

public class PriceServiceImpl implements PriceService {
  private final List<PriceBar> userPriceList = new ArrayList<>();
  private final List<BiConsumer<String, Double>> newPriceConsumer = new ArrayList<>();
  private final List<BiConsumer<String, Double>> postPriceConsumer = new ArrayList<>();

  @Override
  public Double getCurrentPrice(Coin coin) {
    SocketTest mySocket = new SocketTest();
    
//    while(true){
//      Double currentPrice = mySocket.streamlinedTest(coin);
//    }
    
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<PriceBar> getHistoricalData(Coin coin) {
    ApiRequester request = new ApiRequester();
    if (coin.getCoinSymbol() != null) {
      try {
        UrlBuilder myUrl = new UrlBuilder();
        String priceTester = request.sendRequest(myUrl.getHistoricalPriceUrl(coin));
        CryptoParser parse = new CryptoParser();
        Map<String, Object> Json = parse.convertJson(priceTester);

        List<Map<String, Object>> Data = (List<Map<String, Object>>) Json.get("Data");
        List<PriceBar> priceList =
            Data.stream()
                .map(
                    serverPrices -> {
                      PriceBar myPrice = new PriceBar();
                      myPrice.setCoinOpen(Double.parseDouble(serverPrices.get("open").toString()));
                      myPrice.setCoinHigh(Double.parseDouble(serverPrices.get("high").toString()));
                      myPrice.setCoinLow(Double.parseDouble(serverPrices.get("low").toString()));
                      myPrice.setCoinClose(
                          Double.parseDouble(serverPrices.get("close").toString()));
                      myPrice.setCoinTime(Long.parseLong(serverPrices.get("time").toString()));
                      return myPrice;
                    })
                .collect(Collectors.toList());
        return priceList;
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return null;
  }
  
  @Override
  public void addAllUserPriceList(List<PriceBar> list) {
    userPriceList.addAll(list);
  }

  @Override
  public void addNewPriceConsumer(BiConsumer<String, Double> priceConsumer) {
    newPriceConsumer.add(priceConsumer);
  }

  @Override
  public void addPostPriceConsumer(BiConsumer<String, Double> priceConsumer) {
    postPriceConsumer.add(priceConsumer);
  }

  @Override
  public void addSubscription(String coinSymbol) {
    
  }
}
