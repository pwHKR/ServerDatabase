package RemoteCom.Connection.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiClientServer extends Thread {
    public MultiClientServer() {
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(12345);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 2345");
            System.exit(-1);
        }
        Socket clientSocket = null;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                Server server = new Server(clientSocket);
                server.start();
            } catch (IOException e) {
                System.out.println("Accept failed: 2345");
                System.exit(-1);
            }
        }
    }
}
