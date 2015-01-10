package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.UserMap;

public class FetchAttendees extends BaseMainAsyncTask {

    private final String TAG = "FetchAttendeeList";
    private final String URL = GlobalConfiguration.DEFAULT_URL + "eventUsers?event_id=";

    private UserMap userMap;
    private ArrayList<Attendee> attendees;

    public FetchAttendees(MainActivity activity) {
        super(activity);
        this.userMap = UserMap.getInstance();
        this.attendees = activity.getAttendees();
    }

    @Override
    protected Void doInBackgroundOffline(Void... params) {

        attendees.clear();
        userMap.clear();
        userMap.put("48:74:6E:75:64:75", new Attendee("", "iPhone", "iPhoneee", "", ""));

        Attendee attendee;
        attendee = new Attendee("00:00:00:00:00:01", "Demo001", "TestingSortingAlgorithm", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:01", attendee);
        attendee = new Attendee("00:00:00:00:00:02", "Demo002", "Joey", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:02", attendee);
        attendee = new Attendee("00:00:00:00:00:03", "Demo003", "Cherie", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:03", attendee);
        attendee = new Attendee("00:00:00:00:00:04", "Demo004", "Pavan", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:04", attendee);
        attendee = new Attendee("00:00:00:00:00:05", "Demo005", "Kuo", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:05", attendee);
        attendee = new Attendee("00:00:00:00:00:06", "Demo006", "Khoa", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:06", attendee);
        attendee = new Attendee("00:00:00:00:00:07", "Demo007", "MrGun", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:07", attendee);
        attendee = new Attendee("00:00:00:00:00:08", "Demo008", "Alice", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:08", attendee);
        attendee = new Attendee("00:00:00:00:00:09", "Demo009", "Alicia", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:09", attendee);
        attendee = new Attendee("00:00:00:00:00:0A", "Demo010", "Adam", "", "");
        attendees.add(attendee);
        userMap.put("00:00:00:00:00:0A", attendee);

        return null;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {

        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String event_id = sharedPref.getString("EVENT_ID", "");

        attendees.clear();
        userMap.clear();
        doInBackgroundOffline();
        userMap.put("48:74:6E:75:64:75", new Attendee("", "iPhone", "iPhoneee", "", ""));

        if (event_id.equals("")) {
            return null;
        }

        resultJObj = getJSONFromUrl(URL+event_id, Responses.GET);
        Log.d("JSON", resultJObj.toString());
        if (resultJObj != null) {
            try {
                JSONArray array = resultJObj.getJSONArray("user_mappings");
                String macAddress, username, name, gcm_id, photo_url;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    macAddress = item.getString("mac_address");
                    username = item.getString("user_name");
                    name = item.getString("name");
                    gcm_id = item.getString("gcm_id");
                    photo_url = item.getString("photo_url");
                    Attendee attendee = new Attendee(macAddress, username, name, gcm_id, photo_url);
                    attendees.add(attendee);
                    userMap.put(macAddress, attendee);
                }
            } catch (JSONException e) {
                e.getMessage();
            }
        }

        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        Intent intent = new Intent();
        intent.setAction(Action.UPDATE_ATTENDEE_FRAGMENT_ADAPTER);
        activity.sendBroadcast(intent);
    }

}
