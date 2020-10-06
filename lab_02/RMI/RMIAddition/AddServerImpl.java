package lab_02.RMI.RMIAddition;

import java.rmi.*;
import java.rmi.server.*;

public class AddServerImpl extends UnicastRemoteObject implements AddServerInt {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AddServerImpl() throws RemoteException {
    }

    public double add(double d1, double d2) throws RemoteException {
        return d1 + d2;
    }
}