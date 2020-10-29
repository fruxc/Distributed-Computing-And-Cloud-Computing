package lab_04.TokenRing;

import java.io.*;
import java.net.*;

public class Client2 {
	static boolean setSendData;
	static boolean hasToken;

	public static void main(String arg[]) throws Exception {
		InetAddress localhost;
		BufferedReader br;
		String str1;
		ClientInside2 tokenClient;
		ClientInside2 Server;
		while (true) {
			localhost = InetAddress.getLocalHost();
			tokenClient = new ClientInside2(localhost);
			tokenClient.setRecPort(8004);
			tokenClient.setSendPort(9002);
			localhost = InetAddress.getLocalHost();
			Server = new ClientInside2(localhost);
			Server.setSendPort(9000);
			if (hasToken == true) {
				System.out.println("Want to send more data?");
				System.out.println("(Yes/No)?");
				br = new BufferedReader(new InputStreamReader(System.in));
				str1 = br.readLine();
				if (str1.equalsIgnoreCase("yes")) {
					Server.setSendData = true;
					Server.sendData();
					System.out.println("Sent");
				} else if (str1.equalsIgnoreCase("no")) {
					System.out.println("Waiting...");
					tokenClient.sendData();
					hasToken = false;
				}
			} else {
				System.out.println("ENTERING RECIEVING MODE...");
				tokenClient.recData();
				hasToken = true;
			}
		}
	}
}

class ClientInside2 {
	InetAddress localhost;
	int sendPort, recPort;
	boolean setSendData = false;
	boolean hasToken = false;

	ClientInside2(InetAddress localhost) {
		this.localhost = localhost;
	}

	void setSendPort(int sendPort) {
		this.sendPort = sendPort;
	}

	void setRecPort(int recPort) {
		this.recPort = recPort;
	}

	void sendData() throws Exception {
		BufferedReader br;
		String str = "Token";
		DatagramSocket ds;
		DatagramPacket dp;

		if (setSendData == true) {
			System.out.println("Enter the Data");
			br = new BufferedReader(new InputStreamReader(System.in));
			str = "Client Two: " + br.readLine();
			System.out.println("Now sending...");
		}
		ds = new DatagramSocket(sendPort);
		dp = new DatagramPacket(str.getBytes(), str.length(), localhost, sendPort - 1000);
		ds.send(dp);
		ds.close();
		setSendData = false;
		hasToken = false;

	}

	void recData() throws Exception {
		String msgStr;
		byte buffer[] = new byte[256];
		DatagramSocket ds;
		DatagramPacket dp;
		ds = new DatagramSocket(recPort);
		dp = new DatagramPacket(buffer, buffer.length);
		ds.receive(dp);
		ds.close();
		msgStr = new String(dp.getData(), 0, dp.getLength());
		System.out.println("The data is " + msgStr);
		if (msgStr.equals("Token")) {
			hasToken = true;
		}
	}

}