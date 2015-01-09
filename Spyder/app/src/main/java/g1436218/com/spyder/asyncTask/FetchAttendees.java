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
        userMap.put("48:74:6E:75:64:75", "iPhone");

        attendees.add(new Attendee("00:00:00:00:00:01", "Demo001", "TestingSortingAlgorithm"));
        attendees.add(new Attendee("00:00:00:00:00:02", "Demo002", "Joey"));
        attendees.add(new Attendee("00:00:00:00:00:03", "Demo003", "Cherie"));
        attendees.add(new Attendee("00:00:00:00:00:04", "Demo004", "Pavan"));
        attendees.add(new Attendee("00:00:00:00:00:05", "Demo005", "Kuo"));
        attendees.add(new Attendee("00:00:00:00:00:06", "Demo006", "Khoa"));
        attendees.add(new Attendee("00:00:00:00:00:07", "Demo007", "MrGun"));
        attendees.add(new Attendee("00:00:00:00:00:08", "Demo008", "Alice"));
        attendees.add(new Attendee("00:00:00:00:00:09", "Demo009", "Alicia"));
        attendees.add(new Attendee("00:00:00:00:00:0A", "Demo010", "Adam"));

        return null;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {

        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String event_id = sharedPref.getString("EVENT_ID", "");

        attendees.clear();
        userMap.clear();
        userMap.put("48:74:6E:75:64:75", "iPhone");

        if (event_id.equals("")) {
            return null;
        }

        resultJObj = getJSONFromUrl(URL+event_id, Responses.GET);
        if (resultJObj != null) {
            try {
                JSONArray array = resultJObj.getJSONArray("user_mappings");
                String macAddress, username, name;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    Log.i("Object", item.toString());
                    macAddress = item.getString("mac_address");
                    username = item.getString("user_name");
                    name = item.getString("name");
                    attendees.add(new Attendee(macAddress, username, name));
                    userMap.put(macAddress, name);
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
        //Log.i(TAG, userMap.toString());
    }

}
