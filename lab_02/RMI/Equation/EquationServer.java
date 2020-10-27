package lab_02.RMI.Equation;

import java.rmi.Naming;

public class EquationServer {
  public static void main(String args[]) {
    try {
      EquationImpl eq = new EquationImpl();
      Naming.rebind("EquationServer", eq);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}