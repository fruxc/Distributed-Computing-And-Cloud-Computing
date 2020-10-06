package lab_03.RMIBerkeley.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockServerImpl extends UnicastRemoteObject implements ClockServer {

    /**
     *
     */
    private static final long serialVersionUID = -3844930637761365967L;

    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private LocalTime time;

    public ClockServerImpl(LocalTime time) throws RemoteException {
        this.time = time;
    }

    @Override
    public LocalTime getTime() throws RemoteException {
        return time;
    }

    @Override
    public void adjustTime(LocalTime timeClient, long changeInTime) throws RemoteException {
        long localTime = timeClient.toNanoOfDay();
        long thisTime = this.getTime().toNanoOfDay();
        var newTime = thisTime - localTime;
        newTime = newTime * -1 + changeInTime + thisTime;
        LocalTime newLocalTime = LocalTime.ofNanoOfDay(newTime);
        System.out.println("New Time: " + formatter.format(newLocalTime));
        this.time = newLocalTime;
    }

}