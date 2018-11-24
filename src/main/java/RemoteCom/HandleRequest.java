package RemoteCom;

import RemoteCom.Model.Request;

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

            default:

                System.out.println("invalid request");


        }


    }

    public void writeToLogFile(Request request){

        // Add code here
    }


    private void handleDBCall(Request request){

        System.out.println("Reqeust sent " + request.getTime());
        System.out.println("do something with DB" + request.getType());

    }


    private void handleTemp(Request request){

        System.out.println("Request sent " + request.getTime());
        System.out.println("Update temprature on website to " + request.getValue());

    }
}
