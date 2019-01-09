package RemoteCom;

import DB.DBHandler;
import DataStorage.DataStorage;
import RemoteCom.Model.Request;

import javax.xml.crypto.Data;

public class HandleRequest {
    public HandleRequest() {
    }

    public void handle(Request request){


      String type = request.getType();

        switch (type){

            case "dbCall":
                handleDBCall(request);
                break;

            case "tempUpdate":
                handleTemp(request);
                break;

            case "toggleDevice":
                setTemp(request);
                break;


            default:

                System.out.println("invalid request");


        }


    }

    public void writeToLogFile(Request request){

        // Add code here
    }


    private void handleDBCall(Request request){

        System.out.println("In handle DB Call (Handle Request)");

        System.out.println(request.toString());


        DBHandler.getInstance().updateDeviceStatus(String.valueOf(request.getDeviceId()),request.getValue());
        DataStorage.getInstance().setDBUpdated(true);

    }


    private void handleTemp(Request request){

        System.out.println("Request sent " + request.getTime());
        System.out.println("Update temprature on website to " + request.getValue());

    }

    private void setTemp(Request request){


    }
}
