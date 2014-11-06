package g1436218.com.spyder.object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class UserMap extends HashMap<String, String> {

    private final String USER_MAPPING = "user_mapping";
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

    public void updateList(JSONObject jsonObj) {
        if (jsonObj != null) {
            try {
                JSONArray array = jsonObj.getJSONArray(USER_MAPPING);
                String macAddress, userName;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    macAddress = item.getString(MAC_ADDRESS);
                    userName = item.getString(USER_NAME);
                    this.put(macAddress, userName);
                }
            } catch (JSONException e) {
                e.getMessage();
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator iterator = this.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry)iterator.next();
            sb.append(pairs.getKey() + " : " + pairs.getValue() + "\n");
        }
        return sb.toString();
    }
}
