package ru.cft.focusstart;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.print("Enter nick name: ");
        Scanner userInputReader = new Scanner(System.in);
        String userName = userInputReader.nextLine();

        Socket socket = new Socket("localhost", 1111);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread messageListenerThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    System.out.println(reader.readLine());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        messageListenerThread.start();

        while (true) {
            String userInput = userInputReader.nextLine();
            if ("\\q".equals(userInput)) {
                messageListenerThread.interrupt();
                socket.close();
                break;
            }
            String message = userName + ": " + userInput;
            writer.println(message);
            writer.flush();
        }
    }
}
