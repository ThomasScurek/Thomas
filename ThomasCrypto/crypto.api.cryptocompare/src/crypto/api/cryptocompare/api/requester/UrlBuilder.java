package crypto.api.cryptocompare.api.requester;

import crypto.model.coin.Coin;

public class UrlBuilder {
  public Coin coin;
  private String coinListUrl = "https://min-api.cryptocompare.com/data/all/coinlist";

  public String getCurrentPriceUrl(Coin coin) {
    return "https://min-api.cryptocompare.com/data/price?fsym="
        + coin.getCoinSymbol()
        + "&tsyms=USD,JPY,EUR";
  }

  public String getHistoricalPriceUrl(Coin coin) {
        return "https://min-api.cryptocompare.com/data/histoday?fsym="
            + coin.getCoinSymbol()
            + "&tsym=USD&allData=true";
  }

  public String getCoinUrl() {
    return coinListUrl;
  }
}
