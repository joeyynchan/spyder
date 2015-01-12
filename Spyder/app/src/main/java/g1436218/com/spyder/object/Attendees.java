package g1436218.com.spyder.object;

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
        while (iterator.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry)iterator.next();
            sb.append("\n" + pairs.getKey() + " : " + pairs.getValue());
        }
        return sb.toString();
    }
}
