package RemoteServer.Connection.Server;

import RemoteServer.Model.Request;

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
        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

        try {

                Request remote = null;

                try {
                    remote = (Request) in.readObject();
                    System.out.println(remote.toString());

                    /*

                    // Change if statments below to switch case...
                    if(remote.getRequest() == 1){
                        DataStorage.getInstance().getCm().turnLightOn();
                        System.out.println("turn on lamp0 sent from remote");
                    }

                    if(remote.getRequest() == 2){
                        System.out.println("turn off lamp0 sent from remote");
                        DataStorage.getInstance().getCm().turnLightOff();

                    }*/

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



