import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class HexConverterClient extends JFrame implements ActionListener {
    private JTextField hostInput;
    private JTextArea display;
    private JButton convertButton;
    private JButton exitButton;
    private JPanel buttonPanel;
    private static Socket socket = null;

    public HexConverterClient() {
        hostInput = new JTextField(20);
        add(hostInput, BorderLayout.NORTH);
        display = new JTextArea(10, 15);
        display.setWrapStyleWord(true);
        display.setLineWrap(true);
        add(new JScrollPane(display), BorderLayout.CENTER);
        buttonPanel = new JPanel();
        convertButton = new JButton("Get Hex Conversion");
        convertButton.addActionListener(this);
        buttonPanel.add(convertButton);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 12345);
        } catch(Exception e) {
            System.out.println(e);
        }

        SwingUtilities.invokeLater(() -> {
            HexConverterClient frame = new HexConverterClient();
            frame.setSize(400, 300);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    public void actionPerformed(ActionEvent event) {
        String binaryString;
        if (event.getSource() == exitButton) {
            System.exit(0);
        }
        else if (event.getSource() == convertButton) {
            String host = hostInput.getText();

            try {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                binaryString = hostInput.getText();

                output.writeUTF(binaryString);
                String hexString = input.readUTF();

                display.append("The conversion at " + host + " is in hexadecimal: " + hexString);
                hostInput.setText("");
            } catch (UnknownHostException e) {
                display.append("No such host!\n");
                hostInput.setText("");
            } catch (IOException e) {
                display.append(e.toString() + "\n");
            } finally {
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException ioEx) {
                    System.out.println("Unable to disconnect!");
                    System.exit(1);
                }
            }
        }
    }
}
