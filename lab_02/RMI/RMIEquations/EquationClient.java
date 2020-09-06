import java.rmi.*;

public class EquationClient {
  public static void main(String args[]) {
    try {
      String URL = "rmi://localhost/EquationServer";
      Equation equationSolver = (Equation) Naming.lookup(URL);
      System.out.println("a = " + args[0]);
      System.out.println("b = " + args[1]);
      double a = Double.valueOf(args[0]).doubleValue();
      double b = Double.valueOf(args[1]).doubleValue();
      System.out.println("The result of the equation is: " + equationSolver.getEquationResult(a, b));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}