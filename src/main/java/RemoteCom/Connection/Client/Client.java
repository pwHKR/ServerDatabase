package RemoteCom.Connection.Client;

import RemoteCom.Model.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {







   private Socket socket = null;
    private ObjectOutputStream out = null;
    //private ObjectInputStream in = null;
    public Client() {
    }

    private void establishContact() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 5001);
            out = new ObjectOutputStream(socket.getOutputStream());
            //in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host."); System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O");
            System.exit(1);
        }
    }

    private void closeContact() {
        try {
            out.close();
           // in.close();
            socket.close();
        } catch (IOException ioe) { System.out.println("Failed");
            System.exit(-1);
        }
    }


    public void send(Request message){

        establishContact();



        try {
            out.writeObject(message);

        } catch(IOException ex){
            ex.printStackTrace();
        }
        closeContact();

    }




}