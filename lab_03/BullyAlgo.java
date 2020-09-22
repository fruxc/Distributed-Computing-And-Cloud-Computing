package lab_03;

import java.io.*;
import java.util.Scanner;

public class BullyAlgo {
	static int n;
	static int process[] = new int[100];
	static int status[] = new int[100];
	static int co;

	BullyAlgo() {
		n = 0;
		co = 0;
	}

	public static void main(String args[]) throws IOException {
		System.out.println("Enter the number of process");
		Scanner in = new Scanner(System.in);
		n = in.nextInt();

		for (int i = 0; i < n; i++) {
			System.out.println("For process " + (i + 1) + ":");
			System.out.println("Status:");
			status[i] = in.nextInt();
			System.out.println("Priority");
			process[i] = in.nextInt();
		}

		System.out.println("Which process will initiate election?");
		int ele = in.nextInt();

		elect(ele);
		System.out.println("Final coordinator is " + co);
		in.close();
	}

	static void elect(int ele) {
		ele = ele - 1;
		co = ele + 1;
		for (int i = 0; i < n; i++) {
			if (process[ele] < process[i]) {
				System.out.println("Election message is sent from " + (ele + 1) + " to " + (i + 1));
				if (status[i] == 1)
					elect(i + 1);
			}
		}
	}
}