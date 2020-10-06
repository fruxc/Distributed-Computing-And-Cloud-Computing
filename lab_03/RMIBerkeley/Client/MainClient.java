package lab_03.RMIBerkeley.Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import lab_03.RMIBerkeley.Server.*;
import java.time.format.DateTimeFormatter;

public class MainClient {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String[] args) {
		try {
			LocalTime timeLocal = LocalTime.parse("07:00:00", formatter);
			System.out.println("Time Local: " + formatter.format(timeLocal));

			// Server 1
			Registry registry1 = LocateRegistry.getRegistry("localhost", 1500);
			ClockServer cs1 = (ClockServer) registry1.lookup(ClockServerImpl.class.getSimpleName());
			System.out.println("Connected to server one successfully.");
			LocalTime timeServer1 = cs1.getTime();
			System.out.println("Time Server 1: " + formatter.format(timeServer1));

			// Server 2
			Registry registry2 = LocateRegistry.getRegistry("localhost", 1501);
			ClockServer cs2 = (ClockServer) registry2.lookup(ClockServerImpl.class.getSimpleName());
			System.out.println("Connected to server two successfully.");
			LocalTime timeServer2 = cs2.getTime();
			System.out.println("Time Server 2: " + formatter.format(timeServer2));

			// Server 3
			Registry registry3 = LocateRegistry.getRegistry("localhost", 1502);
			ClockServer cs3 = (ClockServer) registry3.lookup(ClockServerImpl.class.getSimpleName());
			System.out.println("Connected to server three successfully.");
			LocalTime timeServer3 = cs3.getTime();
			System.out.println("Time Server 3: " + formatter.format(timeServer3));

			var nanoLocal = timeLocal.toNanoOfDay();
			var diffServer1 = timeServer1.toNanoOfDay() - nanoLocal;
			var diffServer2 = timeServer2.toNanoOfDay() - nanoLocal;
			var diffServer3 = timeServer3.toNanoOfDay() - nanoLocal;
			var avgDiff = (diffServer1 + diffServer2 + diffServer3) / 3;

			cs1.adjustTime(timeLocal, avgDiff);
			cs2.adjustTime(timeLocal, avgDiff);
			cs3.adjustTime(timeLocal, avgDiff);

			timeLocal = timeLocal.plusNanos(avgDiff);

			System.out.println("Updated Time");

			// New time verification
			System.out.println("Time Local: " + formatter.format(timeLocal));
			System.out.println("Time Server 1: " + formatter.format(cs1.getTime()));
			System.out.println("Time Server 2: " + formatter.format(cs2.getTime()));
			System.out.println("Time Server 3: " + formatter.format(cs3.getTime()));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}