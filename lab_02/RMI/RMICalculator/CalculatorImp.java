package lab_02.RMI.RMICalculator;

public class CalculatorImp extends java.rmi.server.UnicastRemoteObject implements Calculator {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // Implementations must have an
    // explicit constructor
    // in order to declare the
    // RemoteException exception
    public CalculatorImp() throws java.rmi.RemoteException {
        super();
    }

    public long add(long a, long b) throws java.rmi.RemoteException {
        return a + b;
    }

    public long sub(long a, long b) throws java.rmi.RemoteException {
        return a - b;
    }

    public long mul(long a, long b) throws java.rmi.RemoteException {
        return a * b;
    }

    public long div(long a, long b) throws java.rmi.RemoteException {
        return a / b;
    }

    public double sqrt(double a) throws java.rmi.RemoteException {
        return Math.sqrt(a);
    }
}
