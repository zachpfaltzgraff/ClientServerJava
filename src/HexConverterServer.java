import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HexConverterServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for clients...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            while (true) {
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

                String binaryString = input.readUTF();
                System.out.println("Binary Value Recieved: " + binaryString);

                String hexString = convertBinaryToHex(binaryString);
                System.out.println("Hex Value Sent: " + hexString);
                System.out.println();

                output.writeUTF(hexString);

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
