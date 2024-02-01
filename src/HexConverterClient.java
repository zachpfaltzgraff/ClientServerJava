import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HexConverterClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            while (true) {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a binary string: ");
                String binaryString = scanner.nextLine().trim();

                System.out.println("Binary Value Sent: " + binaryString);
                output.writeUTF(binaryString);

                String hexString = input.readUTF();
                System.out.println("Hex Value Recieved: " + hexString);

                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
