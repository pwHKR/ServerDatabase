package Arduino;

import com.fazecast.jSerialComm.SerialPort;

public class SenderReceiver {


    public void findSerialPort() {


        for (SerialPort sp : SerialPort.getCommPorts()) {
            System.out.println(sp.getSystemPortName());
        }
        //comPort.openPort();


    }


    public void serialTest() {


        SerialPort comPort = SerialPort.getCommPorts()[3];


        comPort.openPort();


        try {

            for (int i = 0; i < 100; i++) {


                if (i % 2 == 0) {
                    comPort.getOutputStream().write('3');
                    comPort.getOutputStream().write('1');
                    comPort.getOutputStream().flush();

                    while (comPort.getInputStream().available() < 2) {

                    }


                    byte[] input = new byte[comPort.getInputStream().available()];
                    comPort.getInputStream().read(input);
                    System.out.println(input[0]);
                    System.out.println(input[1]);

                    Thread.sleep(2666);

                } else {
                    comPort.getOutputStream().write('3');
                    comPort.getOutputStream().write('0');
                    comPort.getOutputStream().flush();

                    while (comPort.getInputStream().available() < 2) {

                    }


                    byte[] input = new byte[comPort.getInputStream().available()];
                    comPort.getInputStream().read(input);

                    System.out.println(input[0]);
                    System.out.println(input[1]);

                    Thread.sleep(2666);

                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}




    /*

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
    }

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

    */


