package lab_02.RMI.Addition;

import java.rmi.*;

public class AddClient {
  public static void main(String args[]) {
    try {
      String URL = "rmi://localhost/AddServer";
      AddServerInt addServerInt = (AddServerInt) Naming.lookup(URL);
      System.out.println("First No. " + args[0]);
      System.out.println("Second No. " + args[1]);
      double d1 = Double.valueOf(args[0]).doubleValue();
      double d2 = Double.valueOf(args[1]).doubleValue();
      System.out.println("The Sum is: " + addServerInt.add(d1, d2));
    } catch (Exception e) {
    }
  }
}