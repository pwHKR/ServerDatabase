import Arduino.SenderReceiver;
import DB.DBHandler;
import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
        SenderReceiver senderReceiver = new SenderReceiver();


    }
}