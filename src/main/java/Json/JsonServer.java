package Json;

import DB.DBHandler;
import com.google.gson.Gson;

public class JsonServer {

    public JsonServer() {

    }

    public void run() {
        // 1: Ta emot json string

        /*switch (type) {
            case "changeLamp" :

                break;
            default :
                System.out.println("Invalid type!");
                break;
            }*/

        }

        // 2: Utför jobb på arduino
        // 3: Invänta reply från arduino
        // 4: Updatera databasen
        // 5: Uppdatera websidan

        private String splitJsonString(String jsonString, String wantedValue) {
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

