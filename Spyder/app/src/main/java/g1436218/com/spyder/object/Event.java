package g1436218.com.spyder.object;

import java.util.Calendar;

public class Event {

    private String name;
    private String id;
    private String status;
    private String startTime;
    private String endTime;
    private String location;
    private String description;

    public Event (String name, String id, String status, String startTime, String endTime, String location, String description) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
