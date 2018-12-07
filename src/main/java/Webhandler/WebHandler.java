package Webhandler;

import DB.DBHandler;
import DataStorage.DataStorage;
import RemoteCom.Connection.Client.Client;
import RemoteCom.Model.Request;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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
        server.createContext("/operate", new Operate());
        server.createContext("/checkdevice", new Checkdevice());
        server.createContext("/toggleDevice", new ToggleDevice());
        server.createContext("/loginUser", new LoginUser());
        server.setExecutor(null); // creates a default executor
        server.start();

    }


    static class Operate implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {


            System.out.println("in operate");
            String response = "Status: Lamp on";

            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            String requestBody; // Denna kommer innehålla JSON stringen.


            while ((requestBody = br.readLine()) != null) {

                DataStorage.getInstance().setLampChange(true);

                DataStorage.getInstance().setLampOn(!DataStorage.getInstance().isLampOn());

                if (!DataStorage.getInstance().isLampOn()) {

                } else {

                }

                DataStorage.getInstance().setLampChange(false);

                System.out.println("i web While-loopen: " + requestBody);

            }

            // Försöker få ut hela JSON filen. Måste man köra
            // den statisk?!

            jsonString = requestBody;
            //comTalker2.turnLightOn();


            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class Checkdevice implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            System.out.println("in check device");
            Gson gson = new Gson();
            //get body
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);


            //String message from server

            String requestBody = br.readLine();

            String number = requestBody.substring(6, requestBody.length() - 1);

            System.out.println(number);
            //TODO: server gruppens egen metod för att läsa ut relevant data från stringen till objekt


            // This needs to be in every message
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response;

            //TODO: DBhandler tar vårat id och sätter den devicesens isFavrite värde till 1
            String dbValue = DBHandler.getInstance().getDeviceValue(number);
            System.out.println(dbValue);
            //This is were the main logic will be
            // if (success) {}

            //Response string
            response = dbValue;
            if (response != null) {
                //Change message depending on message
                t.sendResponseHeaders(200, response.length());
            } else {
                //Else ( fail) {
                //Om det inte funkade
                //Response string
                response = "Didn't work";
                t.sendResponseHeaders(500, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class LoginUser implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            System.out.println("in login user");

            Gson gson = new Gson();
            //get body
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);


            //String message from server
            String requestBody = br.readLine();

            // Login body contains the java parsed request body
            /** {}*/

            Gson gsonLogin = new Gson();
            LoginBody loginBody = gsonLogin.fromJson(requestBody, LoginBody.class);
            String userExists = DBHandler.getInstance().login(loginBody.getEmail(), loginBody.getPassword());




            /*
            String userInfo = requestBody.substring(9, requestBody.length()-1);
            //System.out.println(userInfo);

            String[] userInfoArray = userInfo.split(",");

            for (int i = 0; i < userInfoArray.length; i++){
                System.out.println(userInfoArray[i]);
            }
            String email = userInfoArray[0].substring(1, userInfoArray[0].length()-1);
            //System.out.println(email);
            String password = userInfoArray[1].substring(12, userInfoArray[1].length()-1);
            //System.out.println(password);

         */


            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            System.out.println(userExists);

            if (userExists.equals("exists")) {
                //Change message depending on message
                t.sendResponseHeaders(200, userExists.length());
                OutputStream os = t.getResponseBody();
                os.write(userExists.getBytes());
                os.close();
            } else {
                t.sendResponseHeaders(500, userExists.length());
                OutputStream os = t.getResponseBody();
                os.write(userExists.getBytes());
                os.close();
            }
           /*
            //TODO: server gruppens egen metod för att läsa ut relevant data från stringen till objekt


            // This needs to be in every message
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response;

            //TODO: DBhandler tar vårat id och sätter den devicesens isFavrite värde till 1
            String dbValue = DBHandler.getInstance().getDeviceValue(deviceId);
            System.out.println(dbValue);

            //kollar om värdet är on i databasen eftersom vi behöver veta om det ska ändras
            if (dbValue.matches("on")) {
                DBHandler.getInstance().updateDeviceStatus(deviceId, "off");
            }
            if (dbValue.matches("off")) {
                DBHandler.getInstance().updateDeviceStatus(deviceId, "on");
            }
            //This is were the main logic will be
            // if (success) {}

            //Response string
            response = dbValue;
            if (response != null) {
                //Change message depending on message
                t.sendResponseHeaders(200, response.length());
            }

            else {
                //Else ( fail) {
                //Om det inte funkade
                //Response string
                response = "Didn't work";
                t.sendResponseHeaders(500, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();*/
        }
    }

    static class ToggleDevice implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {


            System.out.println("in toggle device");

            Gson gson = new Gson();
            //get body
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);


            //String message from server
            String requestBody = br.readLine();


            //plockar ut id ur kass jSon string
            String deviceId = requestBody.substring(6, requestBody.length() - 1);


            System.out.println(requestBody);


            System.out.println(deviceId);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Client newConn = new Client();

                    int id = Integer.valueOf(deviceId.replaceAll("\\D+", ""));

                    newConn.send(new Request("tempUpdate", id, "25"));
                }
            }).start();
            //TODO: server gruppens egen metod för att läsa ut relevant data från stringen till objekt

            //TODO hej ej


            // This needs to be in every message
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response;

            //TODO: DBhandler tar vårat id och sätter den devicesens isFavrite värde till 1
            String dbValue = DBHandler.getInstance().getDeviceValue(deviceId);
            System.out.println(dbValue);

            //kollar om värdet är on i databasen eftersom vi behöver veta om det ska ändras
            if (dbValue.matches("on")) {
                DBHandler.getInstance().updateDeviceStatus(deviceId, "off");
            }
            if (dbValue.matches("off")) {
                DBHandler.getInstance().updateDeviceStatus(deviceId, "on");
            }
            //This is were the main logic will be
            // if (success) {}

            //Response string
            response = dbValue;
            if (response != null) {
                //Change message depending on message
                t.sendResponseHeaders(200, response.length());
            } else {
                //Else ( fail) {
                //Om det inte funkade
                //Response string
                response = "Didn't work";
                t.sendResponseHeaders(500, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.flush(); // added recently
            os.close();


        }

        private Request myHandleJsonString(String jsonString) {
            Gson gson = new Gson();
            String textJson = gson.toJson(jsonString);

            String[] splittedJson = textJson.split("\\W");


            Request request = new Request("updateTemp", Integer.valueOf(splittedJson[11]),
                    splittedJson[17]);


            return request;
        }
    }


    public String getJSON() {
        String theJSON = jsonString;
        return theJSON;
    }


}
