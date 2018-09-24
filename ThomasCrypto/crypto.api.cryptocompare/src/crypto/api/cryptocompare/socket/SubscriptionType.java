package crypto.api.cryptocompare.socket;
 
public class SubscriptionType {
 
      public static final int TRADE = 0;
      public static final int FEEDNEWS = 1;
      public static final int CURRENT = 2;
      public static final int LOADCOMPLETE = 3;
      public static final int COINPAIRS = 4;
      public static final int CURRENTAGG = 5;
      public static final int TOPLIST = 6;
      public static final int TOPLISTCHANGE = 7;
      public static final int ORDERBOOK = 8;
      public static final int FULLORDERBOOK = 9;
      public static final int ACTIVATION = 10;
      public static final int FULLVOLUME = 11;
      public static final int TRADECATCHUP = 100;
      public static final int NEWSCATCHUP = 101;
      public static final int TRADECATCHUPCOMPLETE = 300;
      public static final int NEWSCATCHUPCOMPLETE = 301;
      public static final int BADFORMAT = 401;
}