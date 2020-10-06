package lab_03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	public static SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");
	public static long currentTime;
	private static String serverName;
	private static int serverPort;
	private static long timeNext;

	public static class Update {
		public void run() throws IOException {
			Socket client = new Socket(serverName, serverPort);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			int i = 10;
			out.writeInt(10);
			for (int j = 0; j < i; j++) {
				timeNext += in.readLong();
			}
			timeNext /= i;
			currentTime = timeNext;
			timeNext = 0;
			System.out.println("New Time is " + SDF.format(currentTime));
			client.close();
		}
	}

	public static class InacurateClockTime extends Thread {
		private long changeInTime;

		public InacurateClockTime(long inaccuracy) {
			changeInTime = inaccuracy;
			currentTime = System.currentTimeMillis();
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000 + changeInTime);
					currentTime += 1000;
					System.out.println(SDF.format(currentTime));

				} catch (InterruptedException ex) {
					Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public Client(String serverName, int serverPort) {
		Client.serverName = serverName;
		Client.serverPort = serverPort;

	}

	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("How inaccurate is the clock");
		long inaccuracy = sc.nextLong();
		new Client("localhost", 5000);
		Client.InacurateClockTime time = new InacurateClockTime(inaccuracy);
		Client.Update U = new Update();
		System.out.println("How often check the clock");
		long checkTime = sc.nextLong();
		sc.close();
		time.start();
		while (true) {
			Thread.sleep(checkTime);
			U.run();
		}
	}

}
