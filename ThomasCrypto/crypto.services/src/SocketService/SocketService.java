package SocketService;

import crypto.model.coin.Coin;

public interface SocketService {

  public void subscribeToSocket(Coin coin);

  public void addSubscription(Coin coin);
}
