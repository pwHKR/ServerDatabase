import Arduino.ComTalker;
import DataStorage.DataStorage;
import Webhandler.WebHandler;

import java.io.IOException;


public class Main {


    public static void main(String[] args) {
        String jsonString = "";
        String type = "";
        String id = "";
        String value = "";
        // Server server = new Server();
        // server.run();
        // ComTalker comTalker = new ComTalker();

        DataStorage.getInstance().setLampChange(false);
        DataStorage.getInstance().setLampOn(false);
        WebHandler webHandler = new WebHandler();




        //ComTalker comTalker = new ComTalker();
        try {

            webHandler.startConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Efter startConnection: "+webHandler.getJSON());

        String jsonStringDefault = "{\"type\":\"operate\",\"id\":3}";
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