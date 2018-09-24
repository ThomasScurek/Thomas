package crypto.platform.view.coinlist;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import SocketService.SocketService;
import crypto.services.coin.CoinService;
import crypto.services.price.PriceService;

/** The activator class controls the plug-in life cycle */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String PLUGIN_ID = "crypto.platform.view.coinlist"; // $NON-NLS-1$

  // The shared instance
  private static Activator plugin;
  private static CoinService coinService;
  private static PriceService priceService;
  private static SocketService socketService;
  /** The constructor */
  public Activator() {}

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    ServiceReference<CoinService> serviceReference = context.getServiceReference(CoinService.class);
    coinService = context.getService(serviceReference);

    ServiceReference<PriceService> priceServiceReference =
        context.getServiceReference(PriceService.class);
    priceService = context.getService(priceServiceReference);
    
    ServiceReference<SocketService> socketServiceReference =
        context.getServiceReference(SocketService.class);
    socketService = context.getService(socketServiceReference);
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  public static CoinService getCoinService() {
    return coinService;
  }
  
  public static PriceService getPriceService() {
    return priceService;
  }

  public static SocketService getSocketService() {
    return socketService;
  }
  /**
   * Returns an image descriptor for the image file at the given plug-in relative path
   *
   * @param path the path
   * @return the image descriptor
   */
  public static ImageDescriptor getImageDescriptor(String path) {
    return imageDescriptorFromPlugin(PLUGIN_ID, path);
  }
}
