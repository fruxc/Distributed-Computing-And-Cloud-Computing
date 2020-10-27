package lab_06.LoadBalancing;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/** * RPCServer_Date */
public class LoadBalancer {
	static DatagramSocket serverDatagramSocket;
	static DatagramPacket clientDataPacket;
	static byte buf[];
	static int s1 = 0, s2 = 5;
	static int s2PORT = 5002, s1PORT = 5001;

	public static void main(String[] args) {
		try {
			System.out.println("Load Balancer Daemon up");
			buf = new byte[1024];
			serverDatagramSocket = new DatagramSocket(5000);
			clientDataPacket = new DatagramPacket(buf, buf.length);
			while (true) {
				serverDatagramSocket.receive(clientDataPacket);
				int PORTtoSend = 0;
				String currTime = new String(clientDataPacket.getData(), 0, clientDataPacket.getLength());
				byte[] operationRes = currTime.getBytes();
				if (s1 > s2) {
					PORTtoSend = s2PORT;
					s2++;
				} else {
					PORTtoSend = s1PORT;
					s1++;

				}
				DatagramPacket resDataPacket = new DatagramPacket(operationRes, operationRes.length,
						clientDataPacket.getAddress(), PORTtoSend);
				serverDatagramSocket.send(resDataPacket);
				System.out.println("Sent packet to server at: " + resDataPacket.getPort());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			serverDatagramSocket.close();
		}
	}
}