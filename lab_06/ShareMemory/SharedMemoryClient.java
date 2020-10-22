package lab_06.ShareMemory;

import java.io.*;
import java.net.*;

public class SharedMemoryClient {
	public static void main(String args[]) throws Exception {
		BufferedReader sin;
		PrintStream sout;
		BufferedReader stdin;
		Socket sk = new Socket(InetAddress.getLocalHost(), 2000);
		sin = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		sout = new PrintStream(sk.getOutputStream());
		stdin = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while (true) {
			System.out.println("Client: ");
			s = stdin.readLine();
			sout.println(s);
			s = sin.readLine();
			System.out.println("Server replied: " + s);
			break;
		}
		sin.close();
		sout.close();
		stdin.close();
	}

}
