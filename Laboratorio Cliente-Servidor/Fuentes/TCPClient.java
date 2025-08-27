import java.net.*;
import java.io.*;

// TCP client that connects to the server
public class TCPClient {
  public static void main(String[] args) throws Exception {
    try {
      // Create a socket and connect to the server at localhost (127.0.0.1), port 8888
      Socket socket = new Socket("127.0.0.1", 8888);

      // Input stream to read messages sent from the server
      DataInputStream inStream = new DataInputStream(socket.getInputStream());
      
      // Output stream to send messages to the server
      DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

      // Reader to capture data from the console
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      String clientMessage = "", serverMessage = "";

      // Communication loop: runs until the user types "bye"
      while (!clientMessage.equals("bye")) {
        System.out.println("Enter number :");
        
        // Read a message from the keyboard
        clientMessage = br.readLine(); 

        // Send the message to the server
        outStream.writeUTF(clientMessage);
        outStream.flush();

        // Receive and display the server's response
        serverMessage = inStream.readUTF();
        System.out.println(serverMessage);
      }

      // Close resources when finished
      outStream.close();
      inStream.close();
      socket.close();

    } catch (Exception e) {
      // Print any exception that occurs on the client side
      System.out.println(e);
    }
  }
}
