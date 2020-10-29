package lab_04.RicartAgrawala;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class RicartAgrawala extends Thread {
	int[] ports = { 8081, 8082, 8083, 8084 };
	boolean waiting = false, accessing = false;
	private int hi;
	LinkedList<Processus> Ri = new LinkedList<Processus>(), waitingList = new LinkedList<Processus>(),
			differedList = new LinkedList<Processus>();
	LinkedList<Processus> RiL = new LinkedList<Processus>();
	int ID;
	int port;

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		RicartAgrawala p = new RicartAgrawala(port);
		p.start();
		p.createServer();

	}

	public RicartAgrawala(int port) {
		this.port = port;
		this.ID = port;
	}

	public void run() {
		PrintWriter pw;
		try {
			sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int port : ports) {
			try {
				if (port != ID) {
					Socket s = new Socket("127.0.0.1", port);
					System.out.println("Socket :: " + s.toString());
					Processus p = new Processus(s, port);
					System.out.println("Processus :: " + p.toString());
					pw = new PrintWriter(s.getOutputStream(), true);
					RiL.add(p);
					pw.println(ID);
					p.start();
				}

			} catch (Exception e) {
				System.out.println(e.toString());
				System.out.println("Error Connecting with Other processes");
			}
		}
		while (true) {
			try {
				System.in.read();
				hi++;
				waiting = true;
				for (Processus p : Ri)
					waitingList.add(p);
				sendtoRi(String.valueOf(hi));

				System.out.println("Asking Processes... ");
				while (true) {
					sleep(1500);
					if (waitingList.size() == 0) {
						accessing = true;
						System.out.println("Accessing Critical Section...");
						sleep(5000);
						System.out.println("Done Working on Critical Section!");
						sendtoDiffere("OK");
						differedList.clear();
						waiting = false;
						accessing = false;
						break;
					}

				}
			} catch (Exception e) {
			}
		}

	}

	public void createServer() {
		try (ServerSocket server = new ServerSocket(port)) {
			Processus p;
			while (true) {
				Socket s = server.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
				int id = Integer.parseInt(input.readLine());
				p = new Processus(s, id);
				Ri.add(p);
				System.out.println(id + " is Successfully Connected.");
				sleep(500);
				p.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendtoRi(String message) {
		for (Processus p : Ri)
			p.sendMessage(ID + ":" + message);
	}

	public void sendTo(int x, String message) {
		for (Processus p : Ri)
			if (p.getIdP() == x)
				p.sendMessage(ID + ":" + message);
	}

	public void sendtoAttendu(String message) {
		for (Processus p : waitingList)
			p.sendMessage(ID + ":" + message);
	}

	public void sendtoDiffere(String message) {

		for (Processus p : differedList)
			p.sendMessage(ID + ":" + message);
	}

	class Processus extends Thread {
		BufferedReader input;
		PrintWriter output;
		String msg;
		int id;

		public Processus(Socket client, int id) {
			this.id = id;
			System.out.println("Inside Processus Constructor..");
			try {
				output = new PrintWriter(client.getOutputStream(), true);
				input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public int getIdP() {
			return id;
		}

		public void sendMessage(String str) {
			output.println(str);
		}

		public void run() {
			System.out.println("Inside Processus run method..");
			while (true) {
				try {
					String msg = input.readLine();
					System.out.println("Message Received : " + msg);
					String msgT[] = msg.split(":");
					int rId = Integer.parseInt(msgT[0]);
					if (msgT[1].equals("OK")) {
						for (Processus p : Ri)
							if (p.getIdP() == rId)
								waitingList.remove(p);
					} else {
						int rHi = Integer.valueOf(msgT[1]);
						if (accessing || (waiting && hi > rHi)) {
							for (Processus p : Ri)
								if (p.getIdP() == rId)
									differedList.add(p);
						} else {
							if (hi < rHi)
								hi = rHi;
							sendTo(rId, "OK");
						}
					}
				} catch (IOException e) {
					for (Processus p : Ri)
						if (p.getIdP() == id)
							Ri.remove(p);
					for (Processus p : RiL)
						if (p.getIdP() == id)
							RiL.remove(p);
					for (Processus p : waitingList)
						if (p.getIdP() == id)
							RiL.remove(p);
					for (Processus p : differedList)
						if (p.getIdP() == id)
							RiL.remove(p);
					System.out.println(id + "Error! Socket will be closed immediately");
					break;
				}

			}
		}

	}
}