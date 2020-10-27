package lab_02.RPC.Equation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {
    public static void main(String args[]) throws IOException {

        // Step 1: Establish the socket connection.
        ServerSocket ss = new ServerSocket(4444);
        System.out.println("Server ready");
        Socket s = ss.accept();

        // Step 2: Processing the request.
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true) {
            // wait for input
            String input = dis.readUTF();

            if (input.equals("bye"))
                break;

            System.out.println("Equation received:-" + input);
            int result;

            // Use StringTokenizer to break the equation into operand and
            // operation
            StringTokenizer st = new StringTokenizer(input);

            int oprnd1 = Integer.parseInt(st.nextToken());
            int oprnd2 = Integer.parseInt(st.nextToken());

            // perform the required operation.
            result = (int) (Math.pow(oprnd1, 2) + Math.pow(oprnd2, 2) - 2 * oprnd1 * oprnd2);

            System.out.println("Sending the result...");

            // send the result back to the client.
            dos.writeUTF(Integer.toString(result));
        }
        ss.close();
    }
}