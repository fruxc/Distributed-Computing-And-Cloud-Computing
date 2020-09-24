package lab_05;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BullyAlgo {
	static int n, co;
	static ArrayList<Integer> process = new ArrayList<Integer>();
	static ArrayList<String> status = new ArrayList<String>();

	BullyAlgo() {
		n = 0;
		co = 0;
	}

	static void findCoordinator(int x) {
		x = x - 1;
		co = x + 1;
		for (int i = 0; i < n; i++) {
			if (process.get(x) < process.get(i)) {
				System.out.println("Election message is sent from " + (x + 1) + " to " + (i + 1));
				if (status.get(i) == "yes")
					findCoordinator(i + 1);
			}
		}
	}

	public static void main(String args[]) throws IOException {
		System.out.println("Enter the number of process:");
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();

		for (int i = 0; i < n; i++) {
			System.out.println("For process " + (i + 1) + ":");
			System.out.println("Active Status: (yes/no)");
			status.add(sc.next());
			System.out.println("Priority: (0-10)");
			process.add(sc.nextInt());
		}
		System.out.println("Which process will initiate election:");
		findCoordinator(sc.nextInt());
		System.out.println("Final Co-Ordinator: " + co);
		sc.close();
	}

}