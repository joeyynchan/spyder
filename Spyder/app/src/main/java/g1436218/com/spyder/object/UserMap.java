package g1436218.com.spyder.object;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class UserMap extends HashMap<String, String> {

    private final String TAG = "UserMap";

    private final String USER_MAPPINGS = "user_mappings";
    private final String MAC_ADDRESS = "mac_address";
    private final String USER_NAME = "user_name";
    private int count;

    public static UserMap instance;

    public static UserMap getInstance() {
        if (instance == null) {
            instance = new UserMap();
        }
        return instance;
    }

    private UserMap() {
        super();
        count = 0;
    }

    public void updateList(JSONObject jsonObj) {
        clear();
        if (jsonObj != null) {
            try {
                JSONArray array = jsonObj.getJSONArray(USER_MAPPINGS);
                String macAddress, userName;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    Log.i(TAG, item.toString());
                    macAddress = item.getString(MAC_ADDRESS);
                    if (macAddress.length() <= 10) {
                        macAddress = new Integer(count++).toString();
                    }
                    userName = item.getString(USER_NAME);
                    Log.i(TAG, macAddress + " : " + userName);
                    this.put(macAddress, userName);
                    Log.i(TAG, this.toString());
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
            sb.append("\n" + pairs.getKey() + " : " + pairs.getValue());
        }
        return sb.toString();
    }
}
