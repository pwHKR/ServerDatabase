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
    private static String newRequestValue;


    //public ComTalker comTalker = new ComTalker();


    public WebHandler() {

    }

    public void startConnection() throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        //server.createContext("/operate", new Operate());
        // Kommer antagligen använda toggleDevice istället för operate.
        server.createContext("/checkDevice", new Checkdevice());
        server.createContext("/toggleDevice", new ToggleDevice());
        server.createContext("/loginUser", new LoginUser());
        server.createContext("/setTemp", new SetTemp());
        server.createContext("/registerUser", new RegisterUser());
        server.createContext("/checkCurrentDatabase", new CheckCurrentDatabase());
        server.setExecutor(null); // creates a default executor
        server.start();

    }

    static class Checkdevice implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            Gson gson = new Gson();
            //get body
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            //String message from webclient
            String requestBody = br.readLine();
            Request request = gson.fromJson(requestBody, Request.class);
            String deviceId = String.valueOf(request.getDeviceId());
            System.out.println("Check deviceID: " + deviceId);

            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response;


            new Thread(new Runnable() {
                @Override
                public void run() {



                    Client client = new Client();

                    // Will check a value on the arduino in "labhall". Will later send it back to ServerDatabase
                    // and update the database with current database.

                    // The boolean will be used to check if the database has been updated
                    DataStorage.getInstance().setDBUpdated(false);
                    client.send(new Request("getDeviceStatus",request.getDeviceId(),request.getValue()));
                }
            }).start();

            //check if the database has been updated
            while (DataStorage.getInstance().isDBUpdated == false) {
                // Is idle on purpose
            }
            // Check the updated database value and send it to the webpage.
            String dbValue = DBHandler.getInstance().getDeviceValue(deviceId);
            //System.out.println(dbValue);

            response = dbValue;
            if (response != null) {
                t.sendResponseHeaders(200, response.length());
            } else {
                response = "Didn't work";
                t.sendResponseHeaders(200, response.length());
            }

            //Here will the DBrespons be sent to webserver.
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
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            //String message from server
            String requestBody = br.readLine();


            /*new Thread(new Runnable() {
                @Override
                public void run() {

                }
            });*/

            Gson gsonLogin = new Gson();
            LoginBody loginBody = gsonLogin.fromJson(requestBody, LoginBody.class);
            String userExists = DBHandler.getInstance().login(loginBody.getEmail(), loginBody.getPassword());

            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            System.out.println(userExists);

            if (userExists.equals("exists")) {
                //Change message depending on message
                t.sendResponseHeaders(200, userExists.length());
                OutputStream os = t.getResponseBody();
                os.write(userExists.getBytes());
                os.close();
            } else {
                t.sendResponseHeaders(200, userExists.length());
                OutputStream os = t.getResponseBody();
                os.write(userExists.getBytes());
                os.close();
            }

        }
    }



    static class ToggleDevice implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("in toggle device");

            Gson gson = new Gson();
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            String requestBody = br.readLine();
            Request request = gson.fromJson(requestBody, Request.class);

            String deviceId = String.valueOf(request.getDeviceId());
            //String deviceId = requestBody.substring(6, requestBody.length() - 1);

            System.out.println("Requestbody: " + requestBody);
            System.out.println("toString: " + request.toString());


            //TODO: server gruppens egen metod för att läsa ut relevant data från stringen till objekt


            // This needs to be in every message
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response;

            //TODO: DBhandler tar vårat id och sätter den devicesens isFavrite värde till 1
            String dbValue = DBHandler.getInstance().getDeviceValue(deviceId);
            System.out.println(dbValue);



            //kollar om värdet är on i databasen eftersom vi behöver veta om det ska ändras
            if (dbValue.matches("1")) {
                DBHandler.getInstance().updateDeviceStatus(deviceId, "0");
                newRequestValue = "0";
            }
            if (dbValue.matches("0")) {
                DBHandler.getInstance().updateDeviceStatus(deviceId, "1");
                newRequestValue = "1";
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Client newConn = new Client();

                    int id = Integer.valueOf(deviceId.replaceAll("\\D+", ""));



                    newConn.send(new Request("toggleDevice", request.getDeviceId(), newRequestValue));
                }
            }).start();
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
                t.sendResponseHeaders(200, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.flush(); // added recently
            os.close();


        }
    }

    static class SetTemp implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {

            Gson gson = new Gson();
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            String requestBody = br.readLine();
            Request request = gson.fromJson(requestBody, Request.class);

            String deviceId = String.valueOf(request.getDeviceId());
            //String deviceId = requestBody.substring(6, requestBody.length() - 1);

            System.out.println("Requestbody: " + requestBody);
            System.out.println("toString: " + request.toString());


            //TODO: server gruppens egen metod för att läsa ut relevant data från stringen till objekt


            // This needs to be in every message
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response;



            //kollar om värdet är on i databasen eftersom vi behöver veta om det ska ändras


            new Thread(new Runnable() {
                @Override
                public void run() {
                    Client newConn = new Client();
                    int id = Integer.valueOf(deviceId.replaceAll("\\D+", ""));


                    newConn.send(new Request(request.getType(), request.getDeviceId(), newRequestValue));




                }
            }).start();
            //This is were the main logic will be
            // if (success) {}

            response = "temp set";
            //Response string
            if (response != null) {
                //Change message depending on message
                t.sendResponseHeaders(200, response.length());
            }
            //Kommer aldrig hända om vi inte får tillbaka requestobjektet ifrån arduinot med isValidated.
            /* else {
                //Else ( fail) {
                //Om det inte funkade
                //Response string
                response = "Didn't work";
                t.sendResponseHeaders(200, response.length());
            }*/

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.flush(); // added recently
            os.close();


        }
    }
    static class RegisterUser implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("i regUser");

            Gson gson = new Gson();
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            String requestBody = br.readLine();
            LoginBody newLoginUser = gson.fromJson(requestBody, LoginBody.class);

            //new user skapas i databasen.
            DBHandler.getInstance().createUser(newLoginUser.getUserName(),newLoginUser.getPassword(),newLoginUser.getEmail());

            // This needs to be in every message
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response = "New user created";
            if (response != null) {
                //Change message depending on message
                t.sendResponseHeaders(200, response.length());
            } else {
                //Else ( fail) {
                //Om det inte funkade
                //Response string
                response = "Didn't work";
                t.sendResponseHeaders(200, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.flush(); // added recently
            os.close();


        }
    }
    static class CheckCurrentDatabase implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("in checkDatabase");
            Gson gson = new Gson();
            //get body
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            //String message from webclient
            String requestBody = br.readLine();
            Request request = gson.fromJson(requestBody, Request.class);
            String deviceId = String.valueOf(request.getDeviceId());
            System.out.println("DeviceID to check: "+deviceId);

            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String response;

            String dbValue = DBHandler.getInstance().getDeviceValue(deviceId);
            System.out.println("Value of DeviceID " + deviceId + ": " + dbValue);

            response = dbValue;
            if (response != null) {
                t.sendResponseHeaders(200, response.length());
            } else {
                response = "Didn't work";
                t.sendResponseHeaders(200, response.length());
            }

            //Here will the DBrespons be sent to webserver.
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
