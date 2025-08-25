import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerClientThread extends Thread {
    Socket serverClient;
    int clientNo;

    ServerClientThread(Socket inSocket, int counter) {
        this.serverClient = inSocket;
        this.clientNo = counter;
    }

    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());

            String clientMessage = "", serverMessage = "";

            while (!clientMessage.equals("bye")) {
                clientMessage = inStream.readUTF();
                System.out.println("Client " + clientNo + " says: " + clientMessage);
                serverMessage = "Hello Client " + clientNo + ", you said: " + clientMessage;
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }

            inStream.close();
            outStream.close();
            serverClient.close();

        } catch (IOException e) {
            System.out.println("Error in client thread: " + e);
        }
    }
}
