package crypto.api.cryptocompare.socket;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class CryptoSocket {
  private final Socket socket;
  private final Map<Consumer<String>, Emitter.Listener> listenerCache = new HashMap<>();

  public CryptoSocket() {
    try {
      socket = IO.socket("https://streamer.cryptocompare.com/");
    } catch (URISyntaxException e) {
      e.printStackTrace();
      throw new RuntimeException("Invalid URL encountered");
    }
  }

  public CryptoSocket addSubscriptions(String... subs) {
    Map<String, Object> subscriptions = new HashMap<>();
    subscriptions.put("subs", subs);
    socket.emit("SubAdd", subscriptions);
    return this;
  }

  public CryptoSocket removeSubscriptions(String... subs) {
    Map<String, Object> subscriptions = new HashMap<>();
    subscriptions.put("subs", subs);
    socket.emit("SubRemove", subscriptions);
    return this;
  }

  public CryptoSocket addConsumer(Consumer<String> consumer) {
    Emitter.Listener listener =
        responses -> {
          consumer.accept((String) responses[0]);
        };

    listenerCache.put(consumer, listener);
    socket.on("m", listener);
    return this;
  }

  public CryptoSocket removeConsumer(Consumer<String> consumer) {
    socket.off("m", listenerCache.get(consumer));
    listenerCache.remove(consumer);
    return this;
  }

  public CryptoSocket connect() {
    socket.connect();
    return this;
  }

  public void close() {
    socket.close();
  }
}
