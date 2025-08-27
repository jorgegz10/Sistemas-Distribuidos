import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

// Represents a thread handling communication with a single client
public class ServerClientThread extends Thread {
    Socket serverClient; // Specific socket connection for the client
    int clientNo;        // Client identifier (assigned by the server)

    // Constructor: initializes the client socket and its identifier
    ServerClientThread(Socket inSocket, int counter) {
        this.serverClient = inSocket;
        this.clientNo = counter;
    }

    // This method runs when the thread starts
    public void run() {
        try {
            // Input stream to receive messages from the client
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            
            // Output stream to send messages back to the client
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());

            String clientMessage = "", serverMessage = "";

            // Communication loop: runs until the client sends "bye"
            while (!clientMessage.equals("bye")) {
                // Read message sent by client
                clientMessage = inStream.readUTF();
                System.out.println("Client " + clientNo + " says: " + clientMessage);

                // Prepare a response message for the client
                serverMessage = "Hello Client " + clientNo + ", you said: " + clientMessage;
                
                // Send the response to the client
                outStream.writeUTF(serverMessage);
                outStream.flush(); // Ensure the data is sent immediately
            }

            // Closing resources when the customer finishes
            inStream.close();
            outStream.close();
            serverClient.close();

        } catch (IOException e) {
            // Handle exceptions during client communication
            System.out.println("Error in client thread: " + e);
        }
    }
}

