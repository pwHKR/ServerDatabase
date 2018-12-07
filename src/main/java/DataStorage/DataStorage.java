package DataStorage;


/**
 * Created by Marcus DataSysUtv on 2018-11-12.
 */
public class DataStorage {

    private String clientIP;

    private boolean testLamp;


    public boolean isTestLamp() {
        return testLamp;
    }

    public void setTestLamp(boolean testLamp) {
        this.testLamp = testLamp;
    }

    private boolean isLampChange;

    private boolean isLampOn;

    public boolean isLampOn() {
        return isLampOn;
    }

    public void setLampOn(boolean lampOn) {
        isLampOn = lampOn;
    }

    public boolean isLampChange() {
        return isLampChange;
    }

    public void setLampChange(boolean lampChange) {
        isLampChange = lampChange;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    private static DataStorage ourInstance = new DataStorage();

    public static DataStorage getInstance() {
        return ourInstance;
    }


}
