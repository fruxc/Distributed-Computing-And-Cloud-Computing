package lab_03.RMIBerkeley.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

public interface ClockServer extends Remote {

    LocalTime getTime() throws RemoteException;

    void adjustTime(LocalTime timeClient, long changeInTime) throws RemoteException;
}