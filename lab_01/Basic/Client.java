package lab_01.Basic;

import java.net.*;
import java.io.*;

public class Client {
	private Socket socket = null;
	private BufferedReader input = null;
	private DataOutputStream out = null;

	public Client(String address, int port) {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected to the server: " + socket);
			input = new BufferedReader(new InputStreamReader(System.in));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}

		String line = "";
		while (!line.equals("close")) {
			try {
				line = input.readLine();
				out.writeUTF(line);
			} catch (IOException i) {
				System.out.println(i);
			}
		}
		try {
			System.out.println("Connection Closed!");
			input.close();
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		new Client("127.0.0.1", 65333);
	}
}
