package RemoteServer.Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Message implements Serializable {

    private byte request;
    private Date time;

    public Message(byte request) {
        this.request = request;
        time = Calendar.getInstance().getTime();
    }

    public Date getTime() { return time;
    }

    public byte getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return "Message{" +
                "request=" + request +
                ", time=" + time +
                '}';
    }
}
