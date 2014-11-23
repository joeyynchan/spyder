package g1436218.com.spyder.object;

public class Attendee {

    String username;
    String macAddress;

    public Attendee(String macAddress, String username) {
        this.macAddress = macAddress;
        this.username = username;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return macAddress + " : " + username;
    }

}
