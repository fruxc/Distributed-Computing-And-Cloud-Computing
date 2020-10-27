package lab_02.RMI.Equation;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface Equation extends Remote {
   double getEquationResult(double a, double b) throws RemoteException;
}
