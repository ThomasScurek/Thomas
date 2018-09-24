package crypto.model.pricebar;

import java.io.Serializable;

public class PriceBar implements Serializable {
  private static final long serialVersionUID = 1L;
  private Double coinOpen, coinHigh, coinLow, coinClose;
  private Long coinTime;
  
  public PriceBar(){
    this(null, null, null, null, null);
  }
  
  public PriceBar(Double open, Double high, Double low, Double close, Long time){
    coinOpen = open;
    coinHigh = high;
    coinLow = low;
    coinClose = close;
    coinTime = time;
  }
  
  public void setCoinOpen(Double coinOpen){
    this.coinOpen = coinOpen;
  }
  
  public void setCoinHigh(Double coinHigh){
    this.coinHigh = coinHigh;
  }
  
  public void setCoinLow(Double coinLow){
    this.coinLow = coinLow;
  }
  
  public void setCoinClose(Double coinClose){
    this.coinClose = coinClose;
  }
  
  public void setCoinTime(Long coinTime){
    this.coinTime = coinTime;
  }
  
  public Double getCoinOpen(){
    return coinOpen;
  }
  
  public Double getCoinHigh(){
    return coinHigh;
  }
  
  public Double getLow(){
    return coinLow;
  }
  
  public Double getCoinClose(){
    return coinClose;
  }
  
  public Long getCoinTime(){
    return coinTime;
  }
  
}
