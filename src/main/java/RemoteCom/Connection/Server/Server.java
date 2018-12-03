package RemoteCom.Connection.Server;

import DataStorage.DataStorage;
import RemoteCom.HandleRequest;
import RemoteCom.Model.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Server extends Thread {
    Socket clientSocket = null;


    ObjectInputStream in = null;

    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {

        try {
           // out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            clientSocket.getInetAddress();
            DataStorage.getInstance().setClientIP(clientSocket.getInetAddress().getHostAddress());
            System.out.println(DataStorage.getInstance().getClientIP());

        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

        try {

            HandleRequest handle = new HandleRequest();

                Request remote = null;

                try {
                    remote = (Request) in.readObject();
                    System.out.println(remote.toString());

                    handle.handle(remote);
                    handle.writeToLogFile(remote);


                } catch(IOException ex){ ex.printStackTrace();
                }



        } /*catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }*/ catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            clientSocket.close();
            //out.close();

            in.close();
        } catch (IOException ioe) {
            System.out.println("Failed in closing down");
            System.exit(-1);
        }
    }

    /*
    public void send(byte request){

        try {
             out = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

        try {

            Message remote = null;

            try {
                Message message = new Message(request);

                try {
                    out.writeObject(message);

                } catch(IOException ex){
                    ex.printStackTrace();
                }


        }catch (Exception e){}/*catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }

            clientSocket.close();
            //out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/}



