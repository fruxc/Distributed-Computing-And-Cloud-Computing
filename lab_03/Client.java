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
	public static long Timer;
	private static String serverName;
	private static int serverPort;
	private static long NewTime;

	public static class InternalInaccuratClock extends Thread {

		private long Drift;

		public InternalInaccuratClock(long how_inaacurate) {
			Drift = how_inaacurate;
			Timer = System.currentTimeMillis();
		}

		public void run() {

			while (true) {
				try {
					Thread.sleep(1000 + Drift);
					Timer += 1000;
					System.out.println(SDF.format(Timer));

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

	public static class Update {
		public void run() throws IOException {
			Socket client;
			client = new Socket(serverName, serverPort);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			int i = 10;
			out.writeInt(10);
			for (int j = 0; j < i; j++) {
				NewTime += in.readLong();
			}

			NewTime /= i;

			Timer = NewTime;
			NewTime = 0;
			System.out.println("New Time is " + SDF.format(Timer));
			client.close();
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO code application logic here
		Scanner sc = new Scanner(System.in);
		long clockinaccuracy;
		System.out.println("How inaccurate is the clock");
		clockinaccuracy = sc.nextLong();
		String serverName = "localhost";
		int serverPort = 1333;
		new Client(serverName, serverPort);
		Client.InternalInaccuratClock C = new InternalInaccuratClock(clockinaccuracy);
		Client.Update U = new Update();

		System.out.println("How often check the clock");
		long CC = sc.nextLong();
		C.start();
		while (true) {
			Thread.sleep(CC);
			U.run();
		}
	}

}
