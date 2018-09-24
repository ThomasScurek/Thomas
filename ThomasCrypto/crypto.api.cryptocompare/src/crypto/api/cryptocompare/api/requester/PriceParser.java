package crypto.api.cryptocompare.api.requester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import crypto.model.pricebar.PriceBar;

public class PriceParser {
  public PriceBar setPriceInfo(Map<String, Object> PriceData) {
    PriceBar myPrice = new PriceBar();

    return myPrice;
  }

  public <T> T createObject(String input, Class<T> type) {
    try {
      return new ObjectMapper().readValue(input, type);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public Map<String, Object> convertJson(String input) {
    try {
      return new ObjectMapper().readValue(input, HashMap.class);
    } catch (IOException e) {
      new JsonParseException(null, "Its busted");
      e.printStackTrace();
    }
    return null;
  }

  public JsonParser parseJason(String input) {
    try {
      return new JsonFactory().createParser(input);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}
