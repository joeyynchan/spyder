package g1436218.com.spyder.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Attendees extends HashMap<String, Attendee> {

    private final String TAG = "Attendees";

    private final String USER_MAPPINGS = "user_mappings";
    private final String MAC_ADDRESS = "mac_address";
    private final String USER_NAME = "user_name";

    public static Attendees instance;

    public static Attendees getInstance() {
        if (instance == null) {
            instance = new Attendees();
        }
        return instance;
    }

    private Attendees() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator iterator = this.entrySet().iterator();
        ArrayList<Attendee> attendees = new ArrayList<Attendee>(this.values());
        for (Attendee attendee : attendees) {
            sb.append(attendee.getMacAddress() + " : " + attendee.getUsername() + "\n");
        }
        return sb.toString();
    }
}
