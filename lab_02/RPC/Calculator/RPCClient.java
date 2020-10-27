package lab_02.RPC.Calculator;

import java.io.*;
import java.net.*;

class RPCClient {
	RPCClient() {
		try (DatagramSocket dataSocket = new DatagramSocket(); DatagramSocket dataSocket1 = new DatagramSocket(1300);) {
			System.out.println("\nRPC Client\n");
			System.out.println("Methods:\n"
					+ "1. Add\n2. Sub\n3. Mul\n4. Div\n5. Pow\n6. Mod\n7. Sqrt\n8. Log\n9. Abs\n10. Fact\n11. Cube Root(cbrt)\n12. Sin\n13. Cos\n14. Tan\n15. Expo\n16. Min\n17. Max\n");
			System.out.println("Enter method name and parameter like pow 9 3\n");
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String str = br.readLine();
				byte b[] = str.getBytes();
				DatagramPacket dataPacket = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 1200);
				dataSocket.send(dataPacket);
				dataPacket = new DatagramPacket(b, b.length);
				dataSocket1.receive(dataPacket);
				String s = new String(dataPacket.getData(), 0, dataPacket.getLength());
				System.out.println("\nResult = " + s + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new RPCClient();
	}
}
