import java.rmi.Naming;

public class AddServer {
  public static void main(String args[]) {
    try {
      AddServerImpl ads = new AddServerImpl();
      Naming.rebind("AddServer", ads);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}