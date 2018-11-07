package Arduino;

import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class SenderReceiver {

    //SerialPort comPort = SerialPort.getCommPorts()[0];

    public SenderReceiver() {
        //comPort.openPort();
        //comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
    }

    /*public void sendOutput(char output) {
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
        OutputStream out = comPort.getOutputStream();
    }*/

    public int getInput() {
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        char receivedChar = 0;
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
        InputStream in = comPort.getInputStream();
        try {
            receivedChar = (char) in.read();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();
        return receivedChar;
    }

}
