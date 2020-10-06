package lab_02.RMI.RMICalculator;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class CalculatorClient {

    public static void main(String[] args) {
        try {
            Calculator c = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");
            long d1 = Long.valueOf(args[0]);
            long d2 = Long.valueOf(args[1]);
            System.out.println("Subtraction: " + c.sub(d1, d2));
            System.out.println("Addition: " + c.add(d1, d2));
            System.out.println("Multiplication: " + c.mul(d1, d2));
            System.out.println("Division: " + c.div(d1, d2));
            System.out.println("Square Root: " + c.sqrt((double) d1));
        } catch (MalformedURLException me) {
            System.out.println("MalformedURLException");
            System.out.println(me);
        } catch (RemoteException re) {
            System.out.println("RemoteException");
            System.out.println(re);
        } catch (NotBoundException nbe) {
            System.out.println("NotBoundException");
            System.out.println(nbe);
        } catch (java.lang.ArithmeticException ae) {
            System.out.println("java.lang.ArithmeticException");
            System.out.println(ae);
        }
    }
}
