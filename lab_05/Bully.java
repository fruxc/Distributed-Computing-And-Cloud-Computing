package lab_05;

import java.util.Scanner;

class Process {
	public int id;
	public boolean active;

	public Process(int id) {
		this.id = id;
		this.active = true;
	}
}

public class Bully {
	Scanner sc;
	Process[] processes;
	int noOfProcess;

	public Bully() {
		sc = new Scanner(System.in);
	}

	public void initialiseBully() {
		System.out.println("Enter no of processes:");
		noOfProcess = sc.nextInt();
		processes = new Process[noOfProcess];
		for (int i = 0; i < noOfProcess; i++) {
			processes[i] = new Process(i);
		}
	}

	public void performElection() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\nProcess no " + processes[getMax()].id + " fails");
		processes[getMax()].active = false;

		int InitiatorProcessId = 0;
		boolean notOver = true;
		while (notOver) {

			boolean moreHigherProcesses = false;
			for (int i = InitiatorProcessId + 1; i < noOfProcess; i++) {
				if (processes[i].active) {
					System.out.println("Process " + InitiatorProcessId + " passes election (" + InitiatorProcessId
							+ ") message to process " + i);
					moreHigherProcesses = true;

				}
			}
			System.out.println();
			if (moreHigherProcesses) {

				for (int i = InitiatorProcessId + 1; i < noOfProcess; i++) {
					if (processes[i].active) {
						System.out.println("Process " + i + " passes confirmation OK (" + i + ") message to process "
								+ InitiatorProcessId);
					}

				}
				InitiatorProcessId++;
				System.out.println();
			}

			else {
				int coordinator = processes[getMax()].id;
				System.out.println("Finally Process " + coordinator + " Becomes Coordinator\n");
				for (int i = coordinator - 1; i >= 0; i--) {
					if (processes[i].active) {
						System.out.println("Process " + coordinator + " passes coordinator (" + coordinator
								+ ") message to process " + i);
					}
				}

				System.out.println("\nEnd of Election");
				notOver = false;
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