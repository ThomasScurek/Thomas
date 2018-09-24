package crypto.services;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import SocketService.SocketService;
import crypto.api.cryptocompare.socket.CryptoSocket;
import crypto.api.cryptocompare.socket.SubscriptionType;
import crypto.model.coin.Coin;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketServiceImpl implements SocketService {
  
  private static Logger log = Logger.getLogger(SocketServiceImpl.class);

  @Override
  public void subscribeToSocket(Coin coin) {
    Map<String, Object> subscriptions = new HashMap<>();

    String[] subs =
        new String[] {"5~CCCAGG~" + coin.getCoinSymbol() + "~USD", "11~" + coin.getCoinSymbol()};
    subscriptions.put("subs", subs);

    try {
      Socket socket = IO.socket("https://streamer.cryptocompare.com/");

      socket.emit("SubAdd", subscriptions);

      Emitter.Listener listener =
          args -> {

            //        print(args[0].toString());

          };

      socket.connect().on("m", listener);

      final AtomicInteger count = new AtomicInteger(0);
      Timer timer = new Timer();
      timer.schedule(
          new TimerTask() {

            @Override
            public void run() {
              int i = count.getAndIncrement();
              if (i < 4) {
                Map<String, Object> map = new HashMap<>();
                String[] toRemove = new String[] {subs[i]};
                map.put("subs", toRemove);
                socket.emit("SubRemove", map);
                System.out.println("Removeing sub " + i);
              }
            }
          },
          0,
          5000);

    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void addSubscription(Coin coin) {
    Long hold = new Long(0);

    coin = new Coin("Bitcoin", "BTC", hold, 0.0);

    new CryptoSocket()
        .addSubscriptions(
            SubscriptionType.CURRENTAGG
                + "~CCCAGG~"
                + coin.getCoinSymbol()
                + "~USD" /* , "5~CCCAGG~ETH~USD", "11~BTC", "11~ETH" */)
        .addConsumer(System.out::println)
        .connect();
  }
}
