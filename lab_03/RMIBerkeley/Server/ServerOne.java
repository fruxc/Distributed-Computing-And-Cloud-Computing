package lab_03.RMIBerkeley.Server;

import java.time.format.DateTimeFormatter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

public class ServerOne {
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String[] args) {
		try {
			// Server 1
			ClockServer cs1 = new ClockServerImpl(LocalTime.parse("07:05:00", formatter));
			Registry registry1 = LocateRegistry.createRegistry(1500);
			registry1.rebind(ClockServerImpl.class.getSimpleName(), cs1);
			System.out.println(String.format("Server 1 initiated on port %s", 1500));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}