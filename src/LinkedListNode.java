import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class LinkedListNode {
    public String key;
    public String value;
    public int callCount;
    public DateFormat callTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public LinkedListNode prev;
    public LinkedListNode next;

    public LinkedListNode() {

    }

    public LinkedListNode(String key, String value, int callCount, DateFormat callTime) {
        this.key = key;
        this.value = value;
        this.callCount = callCount;
        this.callTime = callTime;
    }

    @Override
    public String toString() {
        return "URL Node{" +
                "short URL: '" + key + '\'' +
                ", long URL: '" + value + '\'' +
                ", call count: " + callCount +
                ", call time: " + callTime.getCalendar().getTime().toString() +
                '}';
    }
}
