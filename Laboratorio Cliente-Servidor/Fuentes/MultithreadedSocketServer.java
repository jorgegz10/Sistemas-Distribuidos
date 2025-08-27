import java.net.*;
import java.io.*;
// Starts the multithreaded server
public class MultithreadedSocketServer {
    public static void main(String[] args) throws Exception {
        try {
            // Create a ServerSocket object that listens on port 5000
            ServerSocket server = new ServerSocket(5000);
            int counter = 0;
            System.out.println("Server Started ....");
            // Infinite loop to continuously accept new client connections
            while (true) {
                counter++;
                // Accepts the connection request from a client (blocking call)
                Socket serverClient = server.accept(); 
                System.out.println(">> " + "Client No:" + counter + " started!");
                // Create a separate thread to handle this client
                ServerClientThread sct = new ServerClientThread(serverClient, counter); 
                // Start the client-handling thread
                sct.start();
            }
        } catch (Exception e) {
            // Print any exceptions that occur on the server side
            System.out.println(e);
        }
    }
}
