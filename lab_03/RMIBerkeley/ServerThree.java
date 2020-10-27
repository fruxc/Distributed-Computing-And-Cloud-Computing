package lab_03.RMIBerkeley;

import java.time.format.DateTimeFormatter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

public class ServerThree {
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String[] args) {
		try {
			// Server Three
			ClockServer cs3 = new ClockServerImpl(LocalTime.parse("07:15:00", formatter));
			Registry registry1 = LocateRegistry.createRegistry(1502);
			registry1.rebind(ClockServerImpl.class.getSimpleName(), cs3);
			System.out.println(String.format("Server 3 initiated on port %s", 1502));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}