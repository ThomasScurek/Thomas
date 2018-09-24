package crypto.model.coin;

public class Coin {
  private static final long serialVersionUID = 1L;
  private String coinName, coinSymbol;
  private Long coinId;
  private Double currentPrice;

  public Coin() {
    this("", "", null, null);
  }

  public Coin(String name, String symbol, Long id, Double price) {
    coinId = id;
    coinSymbol = symbol;
    coinName = name;
    currentPrice = price;
  }

  public void setCoinSymbol(String coinSymbol) {
    this.coinSymbol = coinSymbol;
  }
  
  public void setCoinName(String coinName) {
    this.coinName = coinName;
  }

  public void setCoinId(Long coinId) {
    this.coinId = coinId;
  }
  
  public void setCurrentPrice(Double currentPrice){
    this.currentPrice = currentPrice;
  }

  public String getCoinSymbol() {
    return coinSymbol;
  }
  
  public String getCoinName() {
    return coinName;
  }

  public Long getCoinId() {
    return coinId;
  }

  public Long getId() {
    return coinId;
  }
  
  public Double getCurrentPrice(){
    return currentPrice;
  }
}
