package lab_06.ShareMemory;

import java.io.*;
import java.net.*;
import java.util.*;

public class SharedMemoryServer {

	static int a = 50;
	static int count = 0;

	public static int getA(PrintStream cout) {
		count++;
		--a;
		cout.println(a);
		return a;
	}

	public void setA(int a) {
		SharedMemoryServer.a = a;
	}

	public static void main(String args[]) throws Exception {
		String op;
		ServerSocket ss = new ServerSocket(2000);
		while (true) {
			Socket sk = ss.accept();
			BufferedReader cin = new BufferedReader(new InputStreamReader(sk.getInputStream()));
			PrintStream cout = new PrintStream(sk.getOutputStream());
			System.out.println("Client request from" + sk.getInetAddress().getHostAddress() + " accept");
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String s;
			s = cin.readLine();
			Scanner sc = new Scanner(s);
			op = sc.next();
			if (op.equalsIgnoreCase("show")) {
			} else {
				cout.println("Check Syntax");
				break;
			}
			System.out.println("Count: " + count);
			sk.close();

			cin.close();
			cout.close();
			stdin.close();
			sc.close();
		} // close while loop ss.close();
		ss.close();
	}

}
