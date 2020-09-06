import java.rmi.*;
import java.rmi.server.*;

public class EquationImpl extends UnicastRemoteObject implements Equation {
    private static final long serialVersionUID = 1L;

    public EquationImpl() throws RemoteException {
    }

    public double getEquationResult(double a, double b) throws RemoteException {
        return ((a*a)+(b*b)-(2*a*b));
    }
}