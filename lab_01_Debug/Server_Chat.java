package lab_01_Debug;

import java.net.*;
import java.util.*;
import java.io.*;

public class Server_Chat {
	static Vector<Socket> ClientSockets;
	static Vector<String> LoginNames;

	public Server_Chat() throws Exception {
		ServerSocket soc = new ServerSocket(5217);
		ClientSockets = new Vector<Socket>();
		LoginNames = new Vector<String>();

		while (true) {

			Socket CSoc = soc.accept();
			AcceptClient obClient = new AcceptClient(CSoc);
		}
	}

	public static void main(String args[]) throws Exception {
		Server_Chat ob = new Server_Chat();
	}

	class AcceptClient extends Thread {
		Socket ClientSocket;
		DataInputStream din;
		DataOutputStream dout;

		AcceptClient(Socket CSoc) throws Exception {
			ClientSocket = CSoc;

			din = new DataInputStream(ClientSocket.getInputStream());
			dout = new DataOutputStream(ClientSocket.getOutputStream());

			String LoginName = din.readUTF();

			System.out.println("User Logged In :" + LoginName);
			LoginNames.add(LoginName);
			ClientSockets.add(ClientSocket);
			start();
		}

		public void run() {
			while (true) {

				try {
					String msgFromClient = new String();
					msgFromClient = din.readUTF();
					StringTokenizer st = new StringTokenizer(msgFromClient);
					String Sendto = st.nextToken();
					String MsgType = st.nextToken();
					int iCount = 0;
					String msg = "";
					while (st.hasMoreTokens()) {
						msg = msg + " " + st.nextToken();
					}
					if (MsgType.equals("LOGOUT")) {
						for (iCount = 0; iCount < LoginNames.size(); iCount++) {
							if (LoginNames.elementAt(iCount).equals(Sendto)) {
								LoginNames.removeElementAt(iCount);
								ClientSockets.removeElementAt(iCount);
								System.out.println("User " + Sendto + " Logged Out ...");
								break;
							}
						}

					} else {
						for (iCount = 0; iCount < LoginNames.size(); iCount++) {
							if (LoginNames.elementAt(iCount).equals(Sendto)) {
								Socket tSoc = (Socket) ClientSockets.elementAt(iCount);
								DataOutputStream tdout = new DataOutputStream(tSoc.getOutputStream());
								tdout.writeUTF(msg);
								break;
							}
						}
						if (iCount == LoginNames.size()) {
							dout.writeUTF("I am offline");
						} else {

						}
					}
					if (MsgType.equals("LOGOUT")) {
						break;
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}
}
