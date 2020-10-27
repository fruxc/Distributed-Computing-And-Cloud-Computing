package lab_04.TokenRing;

import java.net.*;

public class Server {
	public static void main(String agrs[]) throws Exception {
		int port = 8000;
		System.out.println("Server Started on " + port);
		while (true) {
			ServerInside sr = new ServerInside();
			sr.recPort(port);
			sr.recData();
		}
	}
}

class ServerInside {

	boolean hasToken = false;
	boolean sendData = false;
	int recport;

	void recPort(int recport) {
		this.recport = recport;
	}

	void recData() throws Exception {
		byte buff[] = new byte[256];
		DatagramSocket ds;
		DatagramPacket dp;
		String str;

		ds = new DatagramSocket(recport);
		dp = new DatagramPacket(buff, buff.length);
		ds.receive(dp);
		ds.close();

		str = new String(dp.getData(), 0, dp.getLength());
		System.out.println(str);
	}
}