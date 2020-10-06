package lab_02.RMI.RMIAddition;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface AddServerInt extends Remote {
	double add(double d1, double d2) throws RemoteException;
}