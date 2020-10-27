package lab_04;

import java.io.*;
import java.net.*;

public class TokenClient2 {
	static boolean setSendData;
	static boolean hasToken;

	public static void main(String arg[]) throws Exception {
		InetAddress localhost;
		BufferedReader br;
		String str1;
		TokenClientInside2 tokenClient;
		TokenClientInside2 Server;
		while (true) {
			localhost = InetAddress.getLocalHost();
			tokenClient = new TokenClientInside2(localhost);
			tokenClient.setRecPort(8004);
			tokenClient.setSendPort(9002);
			localhost = InetAddress.getLocalHost();
			Server = new TokenClientInside2(localhost);
			Server.setSendPort(9000);
			if (hasToken == true) {

				System.out.println("Do you want to enter the Data –> YES/NO");
				br = new BufferedReader(new InputStreamReader(System.in));
				str1 = br.readLine();
				if (str1.equalsIgnoreCase("yes")) {
					System.out.println("Ready to send");
					Server.setSendData = true;
					Server.sendData();
				} else if (str1.equalsIgnoreCase("no")) {
					System.out.println("Token Waiting...");
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

class TokenClientInside2 {
	InetAddress localhost;
	int sendPort, recPort;
	boolean setSendData = false;
	boolean hasToken = false;

	TokenClientInside2(InetAddress localhost) {
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
		System.out.println("Data sent");
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