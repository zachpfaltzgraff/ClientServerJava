import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HexConverterServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started. Waiting for clients...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            while (true) {
                Scanner input = new Scanner(clientSocket.getInputStream());
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                String binaryString = input.nextLine();

                System.out.println("Binary Value Received: " + binaryString);

                String hexString = convertBinaryToHex(binaryString);
                System.out.println("Hex Value Sent: " + hexString);
                output.println(binaryString + "in hex form is: " + hexString);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertBinaryToHex(String binaryString) {
        int decimalValue = Integer.parseInt(binaryString, 2);
        StringBuilder hexString = new StringBuilder(Integer.toHexString(decimalValue));

        while (hexString.length() < 2) {
            hexString.insert(0, "0");
        }

        return hexString.toString();
    }
}
