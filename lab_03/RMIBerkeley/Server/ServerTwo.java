package lab_03.RMIBerkeley.Server;

import java.time.format.DateTimeFormatter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

public class ServerTwo {
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String[] args) {
		try {
			// Server Two
			ClockServer cs2 = new ClockServerImpl(LocalTime.parse("07:10:00", formatter));
			Registry registry1 = LocateRegistry.createRegistry(1501);
			registry1.rebind(ClockServerImpl.class.getSimpleName(), cs2);
			System.out.println(String.format("Server 2 initiated on port %s", 1501));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}