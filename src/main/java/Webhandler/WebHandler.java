package Webhandler;

import Arduino.ComTalker;
import DataStorage.DataStorage;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.xml.crypto.Data;

/**
 * Created by Marcus DataSysUtv on 2018-11-09.
 */
public class WebHandler {
    private String type = "";
    private String deviceID = "";
    private String value = "";
     public static String jsonString = "";

     //public ComTalker comTalker = new ComTalker();




    public WebHandler() {

    }
        public void startConnection() throws Exception {

            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/test", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

        }


        static class MyHandler implements HttpHandler {

            @Override
            public void handle(HttpExchange t) throws IOException {



                String response = "Status: Lamp on";

                InputStreamReader isr =  new InputStreamReader(t.getRequestBody(),"utf-8");
                BufferedReader br = new BufferedReader(isr);

                String requestBody; // Denna kommer innehålla JSON stringen.
                while ((requestBody = br.readLine())!=null ) {



                    DataStorage.getInstance().setLampChange(true);

                    DataStorage.getInstance().setLampOn(!DataStorage.getInstance().isLampOn());

                    if (!DataStorage.getInstance().isLampOn()){
                        DataStorage.getInstance().getCm().turnLightOff();
                    } else {
                        DataStorage.getInstance().getCm().turnLightOn();
                    }

                    DataStorage.getInstance().setLampChange(false);

                    System.out.println("i web While-loopen: "+requestBody);

                }

                // Försöker få ut hela JSON filen. Måste man köra
                // den statisk?!

                jsonString = requestBody;
                //comTalker2.turnLightOn();




                t.getResponseHeaders().add("Access-Control-Allow-Origin","*");
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

       /* public String getType(){
        String theType;
        theType = type;
        return theType;
        }
        public String getValue(){
            String theValue;
            theValue = value;
            return theValue;
        }
        public String getId(){
            String theID;
            theID = id;
            return theID;
        }
        */
       public String getJSON(){
           String theJSON = jsonString;
           return theJSON;
       }
        public String myHandleJsonString(String jsonString, String wantedValue) {
            Gson gson = new Gson();
            String textJson = gson.toJson(jsonString);
            System.out.println(textJson);
            String[] splittedJson = textJson.split("\\W");
            String returnValue = "";

            if (wantedValue == "type") {
                returnValue = splittedJson[5];
            }
            if (wantedValue == "deviceId") {
                returnValue = splittedJson[11];
            }
            if (wantedValue == "value") {
                returnValue = splittedJson[17];
            }

            return returnValue;
        }

    }
