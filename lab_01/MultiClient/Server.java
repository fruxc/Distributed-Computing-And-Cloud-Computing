package lab_01.MultiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	int port;
	ServerSocket server = null;
	Socket client = null;
	ExecutorService pool = null;
	int clientCount = 0;

	public static void main(String[] args) throws IOException {
		Server newServer = new Server(5000);
		newServer.startServer();
	}

	Server(int port) {
		this.port = port; // Setting up the port
		pool = Executors.newFixedThreadPool(5);
	}

	public void startServer() throws IOException {

		server = new ServerSocket(5000); // Starting up a new server
		System.out.println("Server Started");
		System.out.println("To stop the server please type -1"); // Client count will be -1
		while (true) {
			client = server.accept();
			clientCount++;
			ServerThread runnable = new ServerThread(client, clientCount, this);
			pool.execute(runnable);
		}
	}

	private static class ServerThread implements Runnable {
		Socket client = null;
		BufferedReader in;
		PrintStream out;
		Scanner sc = new Scanner(System.in);
		int id;
		String line;

		ServerThread(Socket client, int count, Server server) throws IOException {

			this.client = client;
			this.id = count;
			System.out.println("Connection " + id + "established with client " + client);

			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintStream(client.getOutputStream());

		}

		@Override
		public void run() {
			int status = 1;
			try {
				while (true) {
					line = in.readLine();
					System.out.print("Client(" + id + ") :" + line + "\n");
					System.out.print("Server : ");
					line = sc.nextLine();
					if (line.equalsIgnoreCase("close")) {
						out.println("closed");
						status = 0;
						System.out.println("Connection ended by server!");
						break;
					}
					out.println(line);
				}

				in.close();
				client.close();
				out.close();
				if (status == 0) {
					System.out.println("Server cleaning up.");
					System.exit(0);
				}
			} catch (IOException ex) {
				System.out.println("Error : " + ex);
			}

		}
	}

}