import DataStorage.DataStorage;
import RemoteCom.Connection.Client.Client;
import RemoteCom.Connection.Server.MultiClientServer;
import RemoteCom.Model.Request;
import Webhandler.WebHandler;


public class Main {


    public static void main(String[] args) {
        String jsonString = "";
        String type = "";
        String id = "";
        String value = "";
        // Json.JsonServer server = new Json.JsonServer();
        // server.run();
        // ComTalker comTalker = new ComTalker();

        DataStorage.getInstance().setLampChange(false);
        DataStorage.getInstance().setLampOn(false);
        DataStorage.getInstance().setTestLamp(false);
        WebHandler webHandler = new WebHandler();




        MultiClientServer multiClientServer = new MultiClientServer();
        multiClientServer.start();

        Request test = new Request("lamp",1,"1");


        // Main server application needs to be executed before the labHall application starts
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Remote Com Test

        Client connection = new Client();
        connection.send(test);



        //ComTalker comTalker = new ComTalker();
        /*try {

            webHandler.startConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Efter startConnection: "+webHandler.getJSON());

        String jsonStringDefault = "{\"type\":\"operate\",\"id\":3}";*/
        //jsonString = webHandler.getJSON();

       // type = webHandler.myHandleJsonString(jsonString,"type");
        //id = webHandler.myHandleJsonString(jsonString,"deviceId");
        //value = webHandler.myHandleJsonString(jsonString,"value");

        /*
        if(type.equalsIgnoreCase("Operate") && value.equalsIgnoreCase("on")){
            comTalker.turnLightOn();
        }
        if(type.equalsIgnoreCase("Operate") && value.equalsIgnoreCase("off")){
            comTalker.turnLightOff();
        }*/


    }
}