package lab_05;

import java.util.Scanner;

public class Bully {
	Scanner sc;
	Process[] processes;
	int n;

	public Bully() {
		sc = new Scanner(System.in);
	}

	public void initialiseBully() {
		System.out.println("Enter no of processes:");
		n = sc.nextInt();
		processes = new Process[n];
		// Initializing processes with ID
		for (int i = 0; i < n; i++) {
			processes[i] = new Process(i);
		}
	}

	public void performElection() {

		try {
			// 1 second sleep before execution
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\nProcess no " + processes[getMax()].id + " fails\n");
		processes[getMax()].active = false;
		// Max ID process failed

		int InitiatorProcessId = 0;
		boolean loop = true;
		while (loop) { // until finds the coordinator

			boolean highProcessID = false;
			for (int i = InitiatorProcessId + 1; i < n; i++) {
				if (processes[i].active) {
					System.out.println("Process " + InitiatorProcessId + " passes election (" + InitiatorProcessId
							+ ") message to process " + i);
					highProcessID = true;
				}
			}
			System.out.println();
			if (highProcessID) { // confirmation from highest process ID

				for (int i = InitiatorProcessId + 1; i < n; i++) {
					if (processes[i].active) {
						System.out.println("Process " + i + " passes confirmation OK (" + i + ") message to process "
								+ InitiatorProcessId);
					}

				}
				InitiatorProcessId++;
				System.out.println();
			}

			else { // if there is no highest process ID in the processes
				int coordinator = processes[getMax()].id;
				System.out.println("Finally Process " + coordinator + " Becomes The Coordinator\n");
				for (int i = coordinator - 1; i >= 0; i--) {
					if (processes[i].active) {
						System.out.println("Process " + coordinator + " passes coordinator (" + coordinator
								+ ") message to process " + i);
					}
				}

				System.out.println("\nEnd of Election");
				loop = false;
				break;
			}
		}

	}

	public int getMax() {
		int maxId = -99;
		int maxIdIndex = 0;
		for (int i = 0; i < processes.length; i++) {
			if (processes[i].active && processes[i].id > maxId) {
				maxId = processes[i].id;
				maxIdIndex = i;
			}
		}
		return maxIdIndex;
	}

	public static void main(String[] args) {
		Bully b = new Bully();
		b.initialiseBully();
		b.performElection();
	}
}