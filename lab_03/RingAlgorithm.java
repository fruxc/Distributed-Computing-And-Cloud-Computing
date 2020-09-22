package lab_03;

import java.awt.event.ActionEvent; // library to listen for any action
import java.awt.event.ActionListener; // library to listen for any action
import java.awt.event.WindowAdapter; // library to listen when a window is closed
import java.awt.event.WindowEvent; // library to listen when a window is closed
import java.io.BufferedReader; // library for buffer to hold the data
import java.io.IOException; // library to handle exception
import java.io.InputStreamReader; // library to use input stream to get the data
import java.io.PrintWriter; // library send the data through writer
import java.net.ServerSocket; // library for Server socket connection
import java.net.Socket; // library for socket connection
import java.util.Date; // library for system date
import java.util.StringTokenizer; // library for String Tokenizer

//Swing(GUI) related libraries
import javax.swing.JButton; // to add buttons on frame
import javax.swing.JFrame; // to hold a frame
import javax.swing.JLabel;
import javax.swing.JPanel; // to create a Panel
import javax.swing.JScrollPane; // to create scrollable panes
import javax.swing.JTextArea; // to create text areas to type or display messages
import javax.swing.border.EmptyBorder; // to provide an empty , transparent border

public class RingAlgorithm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// declare the variables used as public in this class
	public JPanel contentPane; // declare the contentPane for UI
	public JButton btnElect = new JButton("Manual Election"); // create a button in the UI
	public JButton btnCrash = new JButton("Crash"); // create a button in the UI
	public JButton btnReset = new JButton("Reset"); // create a button in the UI
	public JButton btnRefresh = new JButton("Refresh"); // create a button in the UI
	public static JTextArea textArea = new JTextArea(); // create a text area for messages to be displayed
	public JLabel processNo; // create to label in the UI
	public static JTextArea pNo = new JTextArea(); // create a textArea to represent the Process Number
	public JLabel coornatorNo; // create to label in the UI
	public static JTextArea cNo = new JTextArea(); // create a textArea to represent the Coordinator Number

	private static int defPort = 7080; // Default port number, which acts as the starting port
	static String host = "localhost"; // declare the local host name
	static processThread thread = new processThread(); // declare a client thread
	public static int portNo = 0, procNo = 0; // declare the initial port number and process number
	public static boolean portFlag = false; // declare port flag to check the port connection status
	static ServerSocket servSocket; // declare a socket for server
	public static int coordinator = 0, currentAlive = 0, crashedPort = 0; // declare some useful integer variables
	public static boolean isCoord = false, isCrashed = false, isIdle = true, crashFlag = false; // declare some useful
																								// boolean variables

	// Variable to update the process list ***** DE-SCOPED *****
	// public ArrayList<Integer> processList = new ArrayList<Integer>();

	// Constructor which executes the UI for user
	public RingAlgorithm() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 450);
		contentPane = new JPanel(); // create a new panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textArea.setEditable(false); // set the text are to non-editable

		// create a scroll-able pane for the text area
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		scrollPane.setBounds(30, 65, 475, 180);
		contentPane.add(scrollPane);

		// Create a new label and set the position
		processNo = new JLabel("Process Number");
		processNo.setBounds(50, 25, 149, 23);
		contentPane.add(processNo);

		// Create a new text box in order to list the process number
		pNo.setBounds(200, 25, 45, 23);
		pNo.setEditable(false);
		contentPane.add(pNo);

		// Create a Refresh button in order to refresh the token passing
		btnRefresh.setBounds(300, 25, 95, 23);
		btnRefresh.setEnabled(false);
		contentPane.add(btnRefresh);

		// Create a Manual Election button
		btnElect.setBounds(40, 272, 125, 23);
		contentPane.add(btnElect);

		// Create a Crash button
		btnCrash.setBounds(230, 272, 95, 23);
		btnCrash.setEnabled(false);
		contentPane.add(btnCrash);

		// Create a Reset button
		btnReset.setBounds(380, 272, 95, 23);
		btnReset.setEnabled(false);
		contentPane.add(btnReset);

		// Create a new label and set the position
		coornatorNo = new JLabel("New Coordinator");
		coornatorNo.setBounds(50, 330, 149, 23);
		contentPane.add(coornatorNo);

		// Create a new text box in order to list the coordinator number
		cNo.setBounds(200, 340, 45, 23);
		cNo.setEditable(false);
		contentPane.add(cNo);

		// executes this part of code when 'Manual Election' button is clicked
		btnElect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				isIdle = false;

				// This is to update the active process list into an array list whenever Manual
				// Election button is clicked
				// ********** DE-SCOPED *********
				/*
				 * for( int m = 0; m < processList.size(); m++) { processList.remove(m); }
				 * 
				 * int tempPortNo; for(int m=1;m<6;m++) { ServerSocket tempSkt; tempPortNo =
				 * defPort + m; try{ tempSkt= new ServerSocket(tempPortNo); // declare a new
				 * socket tempSkt.close(); // close the socket }catch (IOException ex){
				 * processList.add(m); } }
				 */

				// Initial process which starts the manual election
				textArea.append("\n Token Out: " + "ELECTION " + procNo); // Print the first Token Out command
				String currToken = "ELECTION " + procNo;
				initiateElection(currToken, procNo + 1); // Calling the initiateElection method

				btnElect.setEnabled(false); // set the Election button to be disabled
				btnCrash.setEnabled(false); // set the Crash button to be disabled
				btnRefresh.setEnabled(false); // set the Refresh button to be disabled
				System.out.println("Clicked");

			}
		});

		// executes this part of code when 'Crash' button is clicked
		btnCrash.addActionListener(new ActionListener() {

			@SuppressWarnings("removal")
			@Override
			public void actionPerformed(ActionEvent e) {

				isCrashed = true; // Change the boolean value when crashed

				btnCrash.setEnabled(false); // set the Crash button to be disabled
				btnReset.setEnabled(true); // set the Reset button to be enabled
				btnElect.setEnabled(false); // set the Manual Election button to be disabled
				btnRefresh.setEnabled(false); // set the Refresh button to be disabled

				// Delay the time in before crashing the process
				long startTime = new Date().getTime();
				long currentTime = new Date().getTime();
				while (true) {
					currentTime = new Date().getTime();
					if ((currentTime - startTime) >= 2000) {
						break;
					}
				}

				// Crash the process
				try {
					thread.suspend();
					servSocket.close();
					crashedPort = defPort + procNo; // Assign the crashed port
					textArea.append("\n Processor crashed..!!!");
				} catch (IOException ex) { // Catch actions if not crashed
					isCrashed = false;
					textArea.append("\n Unable to crash this processor, Please try again later..!!!");
				}

				System.out.println("Clicked");

			}
		});

		// executes this part of code when 'Reset' button is clicked
		btnReset.addActionListener(new ActionListener() {

			@SuppressWarnings("removal")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				isCrashed = false;

				btnCrash.setEnabled(true); // set the Crash button to be enabled
				btnReset.setEnabled(false); // set the Reset button to be disabled
				btnElect.setEnabled(true); // set the Manual Election button to be enabled
				btnRefresh.setEnabled(true); // set the Refresh button to be enabled

				// Reset the crashed process
				try {
					servSocket = new ServerSocket(crashedPort);
					thread.informReset(crashedPort, procNo, servSocket); // update the thread variables
					thread.resume();
					textArea.append("\n Thread Restarted..!!  \n Initiating Election..");
					crashedPort = 0;
				} catch (IOException ex) { // Exception to shut down the process when crashed port is unavailable
					System.out.println("\n Port not available for Process Restart..!! \n Process Restart failed..!!");
					System.exit(1);
				}

				isIdle = false; // boolean value change

				// Initiate the election whenever the process is restarted
				textArea.append("\n Token Out: " + "ELECTION " + procNo);
				String currToken = "ELECTION " + procNo;
				initiateElection(currToken, procNo + 1);

				System.out.println("Clicked");

			}
		});

		// executes this part of code when 'Refresh' button is clicked
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Manual Refresh the Alive token whenever the process didn't respond
				thread.nextAlive(procNo + 1);

			}
		});

		// executes this when a window is closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				// inform the previous process whenever a process is removed from the ring
				isIdle = false;
				String currToken = "EXIT " + procNo;
				informExit(currToken, procNo - 1);
				System.out.println("Window Closed, Process " + procNo);
			}
		});
	}

	// Sending the election message to the next process and inform about the
	// election
	public void initiateElection(String token, int proccessN0) {
		// if the process number is more than 5, minus it with 5 inorder to bring the
		// number back within 5
		if (proccessN0 > 5) {
			proccessN0 = proccessN0 - 5;
		}
		try {
			// Create a socket based on the process number
			Socket socket = new Socket(host, defPort + proccessN0);
			// Create the PrintWriter for the socket
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			// send the token into the printwriter for that particular socket
			out.println(token);
			out.flush(); // flush the printwriter output
			out.close(); // close the printwriter
			socket.close(); // close the socket
		} catch (Exception ex) {
			initiateElection(token, proccessN0 + 1); // inform to next next if the next is unavailable
		}
	}

	// Sending the exit message to the previous process in the ring whenever a
	// process is removed
	private void informExit(String token, int procID) {
		if (procID == 0 || procID < 0) {
			if (coordinator != 0) {
				procID = coordinator;
			} else {
				procID = 5;
			}
		}
		try {
			Socket socket = new Socket(host, defPort + procID);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(token);
			out.flush();
			out.close();
			socket.close();
		} catch (Exception ex) {
			initiateElection(token, procID - 1); // inform to previous of previous if previous process is unavailable
		}
	}

	// ******** DE-SCOPED **********
	/*
	 * private void nextAvailablePort(int procID) { if(procID>5) { procID = procID -
	 * 5; } int availPort = defPort + procID; try { servSocket = new
	 * ServerSocket(availPort); procNo = procID; procNo = availPort; String
	 * tempProcess = Integer.toString(procNo); pNo.setText(tempProcess);
	 * textArea.append("\n  Crashed Port is busy, So Connected to the port : " +
	 * portNo); thread.informReset(portNo, procNo, servSocket); thread.resume(); }
	 * catch (IOException e) { if (procID < 6) { nextAvailablePort(procID+1); } else
	 * { //textArea.
	 * append("\n None of the Ports are available for Process Restart..!! \n Process Restart failed..!!"
	 * ); System.out.
	 * println("\n None of the Ports are available for Process Restart..!! \n Process Restart failed..!!"
	 * ); System.exit(1); } } }
	 */

	// Main method
	public static void main(String[] args) {

		RingAlgorithm gui = new RingAlgorithm();
		gui.setVisible(true); // setting the frame visible
		gui.setTitle("Ring Algorithm");

		// open server sockets starting with port 7081. Limit the number to 5
		// Check the available port number before making the connection
		for (int i = 1; i < 6; i++) {
			ServerSocket tempSkt;
			portNo = defPort + i;
			try {
				tempSkt = new ServerSocket(portNo); // declare a new socket
				procNo = portNo - defPort; // Process number identification
				String tempProcess = Integer.toString(procNo);
				pNo.setText(tempProcess); // Inform the GUI
				tempSkt.close(); // close the socket
				textArea.append("Connected to the port : " + portNo);
				portFlag = true;
				break; // break the for loop whenever a port is available

			} catch (IOException e) {
				System.out.println("Socket is running at " + portNo);
			}
		}

		// Limit the process when it exceeds 5
		if (portFlag == false) {
			System.out.println("Exceed the total number of alotted ports.....");
			System.exit(1);
		} else {
			// Connect the identified available port to the server socket
			try {
				servSocket = new ServerSocket(portNo);
				thread.start(); // Start the thread
				thread.init(portNo, procNo, gui, servSocket); // inform the initial variables
			} catch (IOException e) {
				System.out.println("Something went wrong while trying to connect to port " + portNo);
				System.exit(1);
			}
		}
	}

}

//class for handling of threads
class processThread extends Thread {

	// declare all the variables
	public int processNumber;
	public int portNumber;
	public BufferedReader readBuff;
	public PrintWriter printtoNext;
	public RingAlgorithm gui;
	public ServerSocket servSoc;
	public Socket client;
	public int lastportNumber = 0;
	public String procString;
	public int clientportNumber;
	public int defPort = 7080;
	public int coordinator = 0;
	static String host = "localhost"; // declare the local host name

	// thread initializing
	public void init(int portNumber, int processNumber, RingAlgorithm gui, ServerSocket servSoc) {

		this.portNumber = portNumber; // assign the port number
		this.processNumber = processNumber; // assign the process number
		this.gui = gui; // assign the frame
		this.servSoc = servSoc; // assign the socket
		RingAlgorithm.textArea.append("\n Started Thread");
	}

	// thread to update the reset variables
	public void informReset(int portNumber, int processNumber, ServerSocket servSoc) {
		this.portNumber = portNumber; // assign the port number
		this.processNumber = processNumber; // assign the token
		this.servSoc = servSoc; // assign the socket
	}

	// run method of the thread
	public void run() {

		while (true) {

			try {

				client = servSoc.accept(); // Accept the incoming socket
				readBuff = new BufferedReader(new InputStreamReader(client.getInputStream())); // Open input reader

				// if(readBuff.ready()) {

				String token = readBuff.readLine(); // Read the received token

				Thread.sleep(2000);

				// String Tokenize the received token
				StringTokenizer stringTokenizer = new StringTokenizer(token); // Break the token into strings
				switch (stringTokenizer.nextToken()) { // iterate through string's first word
				case "ELECTION": // action to be performed when it is an election message

					// Print the received token
					RingAlgorithm.textArea.append("\n Token In:  " + token);
					RingAlgorithm.isIdle = false;

					try {
						// if the election message reached the initiated process, find the eligible
						// coordinator
						if (Integer.parseInt(stringTokenizer.nextToken()) == processNumber) {
							int[] processes = new int[5];
							processes[0] = processNumber;
							int counter = 1;
							while (stringTokenizer.hasMoreTokens()) {
								processes[counter] = Integer.parseInt(stringTokenizer.nextToken());
								counter++;
							}
							findCoordinator(processes); // find the coordinator
							if (coordinator != 0) {
								// inform the coordinator to other processes
								informCoordinator(coordinator, processNumber, processNumber);
							}
						} else {
							// if not the initiated process, just add the process number to the token and
							// pass the token to next processor
							Thread.sleep(2000);
							RingAlgorithm.textArea.append("\n Token Out:  " + token + " " + processNumber);
							sendToNextProcessor(token + " " + processNumber, processNumber + 1);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				case "COORDINATOR": // action to be performed when it is an coordinator message
					String token2 = stringTokenizer.nextToken(); // coordinator number
					String token3 = stringTokenizer.nextToken(); // identified processor
					try {
						Thread.sleep(2000);
						if (Integer.parseInt(token2) == processNumber) { // if the message is passed to the coordinator
							RingAlgorithm.isCoord = true;
							RingAlgorithm.textArea
									.append("\n This is the new Coordinator..!!! Elected by the Processor " + token3);
						} else { // informing the previous coordinator about the new coordinator
							if (RingAlgorithm.isCoord == true) {
								RingAlgorithm.textArea.append("\n" + processNumber + " is not coordinator anymore");
								RingAlgorithm.isCoord = false;
							}
						}
						// if the coordinator message is circled backed to the elector
						if (Integer.parseInt(token3) == processNumber) {
							RingAlgorithm.textArea.append(
									"\n New Coordinator is elected ---> " + token2 + " and informed all other process");
							gui.btnElect.setEnabled(true); // Enable the Manual Election button
							gui.btnRefresh.setEnabled(true); // Enable the refresh button
							if (RingAlgorithm.isCrashed == false) { // Enable the Crash button if not crashed
								gui.btnCrash.setEnabled(true);
							}
							RingAlgorithm.isIdle = true;
							// Start sending the Alive token in order to keep the ring process in sync
							if (processNumber == coordinator) { // if the elector is the coordinator
								nextAlive(processNumber + 1); // then start the alive message from the next available
																// processor
							} else { // or else start the alive message from the current processor
								String tokenAlive = "ALIVE " + coordinator + " " + processNumber;
								verifyAlive(RingAlgorithm.coordinator, processNumber + 1, processNumber + 1,
										tokenAlive);
							}
						} else { // if the coordinator message is received by other processor
							if (!RingAlgorithm.isCoord) { // Print the new coordinator info
								RingAlgorithm.textArea
										.append("\n New Coordinator is --> " + token2 + "  Elected By " + token3);
							}
							RingAlgorithm.cNo.setText(token2); // update the GUI
							// update the variables
							RingAlgorithm.coordinator = Integer.parseInt(token2);
							coordinator = Integer.parseInt(token2);
							sendToNextProcessor(token, processNumber + 1); // send the message to next processor
							RingAlgorithm.isIdle = true;
							gui.btnCrash.setEnabled(true); // Crash button is enabled
							gui.btnRefresh.setEnabled(true); // Refresh button is enabled
							if (RingAlgorithm.currentAlive == processNumber - 1) {
								// initiate the alive message when the last alive message was stopped here
								if (processNumber == coordinator) {
									nextAlive(processNumber + 1);
								} else {
									String tokenAlive = "ALIVE " + coordinator + " " + processNumber;
									verifyAlive(RingAlgorithm.coordinator, processNumber + 1, processNumber,
											tokenAlive);
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				case "ALIVE": // if the message is an ALIVE message
					String tokenCNo = stringTokenizer.nextToken(); // coordinator number
					String tokenPNo = stringTokenizer.nextToken(); // process number
					if (RingAlgorithm.isIdle == true) {
						try {
							// if the ALIVE message is circled back to the initiator
							if (Integer.parseInt(tokenPNo) == processNumber) {
								// check the status
								if (stringTokenizer.hasMoreTokens()) {
									if (stringTokenizer.nextToken().equals("OK")) {
										// if the status is OK update the GUI
										RingAlgorithm.textArea.append("\n [" + tokenPNo + "] --> Coordinator Alive");
										if (processNumber + 1 == coordinator) {
											nextAlive(processNumber + 2);
										} else {
											nextAlive(processNumber + 1);
										}
									}
								} else { // else initiate the elction process
									RingAlgorithm.textArea
											.append("\n Coordinator not responding... \n Initiating new election...");
									RingAlgorithm.textArea.append("\n Token Out: " + "ELECTION " + processNumber);
									String currToken = "ELECTION " + processNumber;
									gui.initiateElection(currToken, processNumber + 1);
								}

							} else if (Integer.parseInt(tokenCNo) == processNumber) { // if the alive message is
																						// received by the coordinator
								RingAlgorithm.textArea
										.append("\n [" + tokenPNo + "] --> Coordinator Validation Passed");
								// append OK status and send it back to the initiator
								token = token + " OK";
								sendToNextProcessor(token, processNumber + 1);
							} else { // else forward it to the next processor
								sendToNextProcessor(token, processNumber + 1);
							}

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					break;
				case "EXIT": // if it is EXIT message
					// Initiate the election process since the next processor was removed from the
					// ring
					String token4 = stringTokenizer.nextToken();
					RingAlgorithm.isIdle = false;
					RingAlgorithm.textArea
							.append("\n Process " + token4 + " is removed from the Ring, Initiating Election.. ");
					String electToken = "ELECTION " + processNumber;
					RingAlgorithm.textArea.append("\n Token Out: " + electToken);
					sendToNextProcessor(electToken, processNumber + 1);
					break;
				case "NEXT_ALIVE": // if it is NEXT_ALIVE message
					// send the alive message from this processor since the previous one was a
					// coordinator
					int targetNo = Integer.parseInt(stringTokenizer.nextToken());
					if (targetNo == processNumber) {
						if (targetNo == coordinator) {
							nextAlive(1);
						} else {
							RingAlgorithm.currentAlive = targetNo - 1;
							String tokenNextAlive = "ALIVE " + coordinator + " " + processNumber;
							verifyAlive(RingAlgorithm.coordinator, processNumber + 1, processNumber, tokenNextAlive);
						}
					}
				}

				/*
				 * gui.textArea.append("\n Token " + token);
				 * 
				 * if(token != null) { gui.textArea.append("\n Started Run Thread Null"); }
				 */
				// }

			} catch (IOException e) {
				// Logger.getLogger(RingAlgorithm.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("Exception : " + e);
			} catch (NullPointerException e) {
				// Logger.getLogger(RingAlgorithm.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("Exception : " + e);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Exception : " + e);
			} finally {
				try {
					readBuff.close();
				} catch (IOException ex) {
					System.out.println("Exception : " + ex);
				}
			}
		}
	}

	// Sending the token message to the next process
	private void sendToNextProcessor(String token, int procID) {
		if (procID > 5) {
			procID = procID - 5;
		}
		try {
			Socket socket = new Socket(host, defPort + procID);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(token);
			out.flush();
			out.close();
			socket.close();
		} catch (Exception ex) {
			sendToNextProcessor(token, procID + 1); // send it to next next if the next one was unavailable
		}
	}

	// After Election tokens are passed, check which process becomes coordinator
	private void findCoordinator(int[] processes) {
		int newCoordinator = processes[0];
		// Check the first process number and compare with other live processes and
		// elect Coordinator with highest process number
		for (int i = 1; i < processes.length; i++) {
			if (processes[i] > newCoordinator) {
				newCoordinator = processes[i];
			}
		}

		// assign the variables with the new coordinator value
		RingAlgorithm.coordinator = newCoordinator;
		coordinator = newCoordinator;
		RingAlgorithm.cNo.setText(Integer.toString(newCoordinator));

	}

	// After confirming the coordinator, check which process becomes coordinator
	private void informCoordinator(int coord, int procNo, int electedNo) {
		int tempProc;
		if (coord == procNo) {
			tempProc = 1;
		} else {
			tempProc = procNo + 1;
		}
		// initiate the coordinator token and pass it to all the active processes
		String token = "COORDINATOR " + coord + " " + electedNo;
		try {
			Socket socket = new Socket(host, defPort + tempProc);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(token);
			out.flush();
			out.close();
			socket.close();
		} catch (Exception ex) {
			informCoordinator(coord, tempProc, electedNo); // inform to next next if the next one was unavailable
		}
	}

	// method to pass the alive message to the next processor from the initiator
	private void verifyAlive(int coordID, int procID, int electedID, String token) {
		if (RingAlgorithm.isIdle == true) {
			long time = 1500 * electedID;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (procID > 5) {
				procID = procID - 5;
			}
			try {
				Socket socket = new Socket(host, defPort + procID);
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				out.println(token);
				out.flush();
				out.close();
				socket.close();
				RingAlgorithm.currentAlive = electedID;
			} catch (Exception ex) {
				verifyAlive(coordID, procID + 1, electedID, token); // pass to next next if next was unavailable
			}
		}
	}

	// Method to initiate the NEXT_ALIVE message from the current processor
	public void nextAlive(int nextProc) {
		if (nextProc > 5) {
			nextProc = nextProc - 5;
		}
		String token = "NEXT_ALIVE " + nextProc;
		try {
			Socket socket = new Socket(host, defPort + nextProc);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(token);
			out.flush();
			out.close();
			socket.close();
		} catch (Exception ex) {
			// forward to next next if the next one was unavailable
			if (nextProc + 1 == coordinator) {
				nextAlive(nextProc + 2);
			} else {
				nextAlive(nextProc + 1);
			}
		}
	}

}
