package ru.cft.focusstart;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Server {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getResourceAsStream("/server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        }

        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
        List<Socket> clients = new ArrayList<>();
        List<BufferedReader> readers = new ArrayList<>();
        List<PrintWriter> writers = new ArrayList<>();

        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    String message = null;
                    for (BufferedReader reader : readers) {
                        if (reader.ready()) {
                            message = reader.readLine();
                            break;
                        }
                    }

                    if (message != null) {
                        System.out.println("new message received: " + message);
                        for (PrintWriter writer : writers) {
                            writer.println(message);
                            writer.flush();
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        });
        messageListenerThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                serverSocket.close();
                for (Socket socket : clients) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }));

        //noinspection InfiniteLoopStatement
        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            readers.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
            writers.add(new PrintWriter(clientSocket.getOutputStream()));
        }
    }
}
