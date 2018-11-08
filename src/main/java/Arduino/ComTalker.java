package Arduino;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

public class ComTalker {

    SerialPort comPort;

    public ComTalker() {
        this.comPort = SerialPort.getCommPorts()[2];

        comPort.openPort();
    }

    public void turnLightOff() {
        try {
            comPort.getOutputStream().write('3');

            comPort.getOutputStream().write('0');
            comPort.getOutputStream().flush();


            while (comPort.getInputStream().available() < 2) {

            }


            byte[] input = new byte[comPort.getInputStream().available()];
            comPort.getInputStream().read(input);

            System.out.println(input[0]);
            System.out.println(input[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void turnLightOn() {
        try {
            comPort.getOutputStream().write('3');

            comPort.getOutputStream().write('1');
            comPort.getOutputStream().flush();


            while (comPort.getInputStream().available() < 2) {

            }



            byte[] input = new byte[comPort.getInputStream().available()];
            comPort.getInputStream().read(input);

            System.out.println(input[0]);
            System.out.println(input[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
