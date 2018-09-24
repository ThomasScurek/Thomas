package crypto.api.cryptocompare.socket;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import crypto.model.coin.Coin;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketTest {
  private Double currentPrice;
  private static Consumer<String> consumer;

//  public static void main(String[] args) {
//    streamlinedTest(null);
//
//    while(true){
//    }
//  }

  
  
//  public Consumer<String> getConsumer(){
//    return consumer;
//  }
  
  public Double getCurrentPrice(){
    return currentPrice;
  }

  public static void print(String message) {
    System.out.println(message);
  }
}
