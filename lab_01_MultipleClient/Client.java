package lab_01_MultipleClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {

	public static void main(String args[]) throws Exception {
		Socket socket = new Socket("127.0.0.1", 5000); // New Connection
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input server
		PrintStream out = new PrintStream(socket.getOutputStream()); // Client output
		BufferedReader clientIn = new BufferedReader(new InputStreamReader(System.in)); // Client input
		String line;
		while (true) {
			System.out.print("Client: ");
			line = clientIn.readLine();
			out.println(line);
			if (line.equalsIgnoreCase("close")) { // To close connection
				System.out.println("Connection ended by client!!");
				break;
			}
			line = in.readLine();
			System.out.print("Server: " + line + "\n");
		}
		socket.close();
		in.close();
		out.close();
		clientIn.close();
	}
}