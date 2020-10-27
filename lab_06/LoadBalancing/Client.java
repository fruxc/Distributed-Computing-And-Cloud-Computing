package lab_06.LoadBalancing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	static DatagramPacket msgDatagramPacket;
	static DatagramSocket clientDatagramSocket;
	static byte[] data;
	static BufferedReader br;
	static int LB_PORT = 5000;

	public static void main(String[] args) {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			data = new byte[1024];

			clientDatagramSocket = new DatagramSocket();
			while (true) {
				System.out.println("Msg: ");
				String msg = br.readLine();
				data = msg.getBytes();
				msgDatagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), LB_PORT);
				clientDatagramSocket.send(msgDatagramPacket);
				System.out.println("Packet sent to server at PORT: " +

						LB_PORT);

				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clientDatagramSocket.close();
		}
	}
}