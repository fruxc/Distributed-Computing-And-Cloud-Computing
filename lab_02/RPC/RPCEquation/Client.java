import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        int port = 4444;
        Scanner sc = new Scanner(System.in);

        // Step 1: Open the socket connection.
        Socket s = new Socket(ip, port);

        // Step 2: Communication-get the input and output stream
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true) {
            // Enter the equation in the form-
            // "operand1 operation operand2"
            System.out.print("Enter the values of a and b: ");
            System.out.println("'value of a' 'value of b' ");

            String inp = sc.nextLine();

            if (inp.equals("bye"))
                break;

            // send the equation to server
            dos.writeUTF(inp);

            // wait till request is processed and sent back to client
            String ans = dis.readUTF();
            System.out.println("(a-b)^2 = a^2 + b^2 - 2ab = " + ans);
        }
        sc.close();
        s.close();
    }
}