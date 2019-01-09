package RemoteCom.Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Request implements Serializable {

    private String type;
    private int id;
    private String value;
    private Date time;
    private boolean isValidated;


    public Request(String type, int id, String value) {
        this.type = type;
        this.id = id;
        this.value = value;

        time = Calendar.getInstance().getTime();
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getDeviceId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Date getTime() {
        return time;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", deviceId=" + id +
                ", value='" + value + '\'' +
                ", time=" + time +
                '}';
    }

}
