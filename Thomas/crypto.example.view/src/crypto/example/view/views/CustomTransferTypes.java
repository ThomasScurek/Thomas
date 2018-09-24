package crypto.example.view.views;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TransferData;

import crypto.api.Person;

public class CustomTransferTypes extends ByteArrayTransfer {
  /*
   * The data being transferred is an <bold>array of type MyType</bold> where
   * MyType is define as:
   */
  private static Logger log = Logger.getLogger(CustomTransferTypes.class);

  private static final String MYTYPENAME = "name_for_my_type";

  private static final int MYTYPEID = registerType(MYTYPENAME);

  private static CustomTransferTypes _instance = new CustomTransferTypes();

  public static CustomTransferTypes getInstance() {
    return _instance;
  }

  public void javaToNative(Object object, TransferData transferData) {
    boolean checkMyType = checkMyType(object);
    log.info(checkMyType);
    log.info(object);
    if (!checkMyType || !isSupportedType(transferData)) {
      DND.error(DND.ERROR_INVALID_DATA);
    }
    byte[] bytes = null;

    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos)) {
      oos.writeObject(object);
      oos.flush();
      bytes = bos.toByteArray();
    } catch (IOException e) { // TODO Auto-generated catch block
      log.error("failure to serialize object", e);
    }
    super.javaToNative(bytes, transferData);
  }

  public Object nativeToJava(TransferData transferData) {
    if (isSupportedType(transferData)) {
      byte[] buffer = (byte[]) super.nativeToJava(transferData);
      if (buffer == null) return null;

      Object obj = null;
      try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
          ObjectInputStream ois = new ObjectInputStream(bis)) {
        obj = ois.readObject();
      } catch (IOException | ClassNotFoundException e) { // TODO Auto-generated catch block
        log.error("failure to deserialize byte array", e);
      }
      return obj;
    }
    return null;
  }

  protected String[] getTypeNames() {
//    log.info(MYTYPENAME);
    return new String[] {MYTYPENAME};
  }

  protected int[] getTypeIds() {
//    log.info(MYTYPEID);
    return new int[] {MYTYPEID};
  }

  boolean checkMyType(Object object) {
    if (object == null || !(object instanceof Person)) {
      return false;
    }
    return true;
  }

  protected boolean validate(Object object) {
    return checkMyType(object);
  }
}
