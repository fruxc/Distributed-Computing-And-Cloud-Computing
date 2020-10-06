package lab_03.RPCCristian;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {
	private final ServerSocket serverSocket;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void run(){
		while (true) {
			try {
				Socket server = serverSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				int i = in.readInt();
				for (int j = 0; j < i; j++) {
					Thread.sleep((long) (100 + new Random().nextInt(51)));
					out.writeLong(System.currentTimeMillis());
				}
				server.close();
			} catch (UnknownHostException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException | InterruptedException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 5000;
		Thread t = new Server(port);
		t.start();
	}

}