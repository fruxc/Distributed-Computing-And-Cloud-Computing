package lab_04.TokenRing;

import java.io.*;
import java.net.*;

public class Client1 {
	public static void main(String arg[]) throws Exception {
		InetAddress localhost;
		BufferedReader br;
		String str = "";
		ClientInside1 tokenClient, tokenServer;

		while (true) {
			localhost = InetAddress.getLocalHost();
			tokenClient = new ClientInside1(localhost);
			tokenServer = new ClientInside1(localhost);
			tokenClient.setSendPort(9004);
			tokenClient.setRecPort(8002);
			localhost = InetAddress.getLocalHost();
			tokenServer.setSendPort(9000);

			if (tokenClient.hasToken == true) {
				System.out.println("Do you want to enter the Data –> Yes/No?");
				br = new BufferedReader(new InputStreamReader(System.in));
				str = br.readLine();
				if (str.equalsIgnoreCase("yes")) {
					System.out.println("Ready to send");
					tokenServer.setSendData = true;
					tokenServer.sendData();
					tokenServer.setSendData = false;
				} else if (str.equalsIgnoreCase("no")) {
					System.out.println("Waiting...");
					tokenClient.sendData();
					tokenClient.recData();
				}
			} else {
				System.out.println("ENTERING RECEIVING MODE...");
				tokenClient.recData();
			}
		}
	}

}

class ClientInside1 {
	InetAddress localhost;
	int sendPort, recPort;
	boolean hasToken = true;
	boolean setSendData = false;

	ClientInside1(InetAddress localhost) {
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
			System.out.println("Enter the Data:");
			br = new BufferedReader(new InputStreamReader(System.in));
			str = "Client One: " + br.readLine();
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
		String msgstr;
		byte buffer[] = new byte[256];
		DatagramSocket ds;
		DatagramPacket dp;

		ds = new DatagramSocket(recPort);
		dp = new DatagramPacket(buffer, buffer.length);
		ds.receive(dp);
		ds.close();
		msgstr = new String(dp.getData(), 0, dp.getLength());
		System.out.println("The data is " + msgstr);

		if (msgstr.equals("Token")) {
			hasToken = true;
		}
	}

}
