package g1436218.com.spyder.object;

public class Attendee {

    String name;
    String username;
    String macAddress;

    public Attendee(String macAddress, String username, String name) {
        this.macAddress = macAddress;
        this.username = username;
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {return name;}

    public String toString() {
        return macAddress + " : " + username;
    }

}
