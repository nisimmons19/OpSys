import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CombinedSocketServerClient {
    private static final int SERVER_PORT = 12345;
    private static final String SERVER_ADDRESS = "localhost";

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("Server is listening on port " + SERVER_PORT);

                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                        System.out.println("Client connected from " + clientSocket.getInetAddress());

                        String clientMessage;
                        while ((clientMessage = in.readLine()) != null) {
                            System.out.println("Received from client: " + clientMessage);
                            out.println("Server received your message: " + clientMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread clientThread = new Thread(() -> {
            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

                System.out.println("Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);

                String userInput;
                while ((userInput = consoleReader.readLine()) != null) {
                    out.println(userInput); // Send user input to the server

                    String serverResponse = in.readLine(); // Receive server response
                    System.out.println("Server response: " + serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start both server and client threads
        serverThread.start();
        clientThread.start();
    }
}
