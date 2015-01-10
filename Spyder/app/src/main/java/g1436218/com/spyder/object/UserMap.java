package g1436218.com.spyder.object;

import java.util.HashMap;
import java.util.Iterator;

public class UserMap extends HashMap<String, Attendee> {

    private final String TAG = "UserMap";

    private final String USER_MAPPINGS = "user_mappings";
    private final String MAC_ADDRESS = "mac_address";
    private final String USER_NAME = "user_name";

    public static UserMap instance;

    public static UserMap getInstance() {
        if (instance == null) {
            instance = new UserMap();
        }
        return instance;
    }

    private UserMap() {
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
