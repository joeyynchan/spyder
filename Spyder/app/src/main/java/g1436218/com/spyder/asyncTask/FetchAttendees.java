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
import g1436218.com.spyder.dialogFragment.AlertFragment;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Attendees;

public class FetchAttendees extends BaseMainAsyncTask {

    private final String TAG = "FetchAttendeeList";
    private final String URL = GlobalConfiguration.DEFAULT_URL + "eventUsers?event_id=";

    private Attendees attendees;

    public FetchAttendees(MainActivity activity) {
        super(activity);
        this.attendees = Attendees.getInstance();
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {

        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String event_id = sharedPref.getString("EVENT_ID", "");


        attendees.clear();
        //attendees.put("48:74:6E:75:64:75", new Attendee("", "iPhone", "iPhoneee", "", ""));

        if (event_id.equals("")) {
            return null;
        }

        resultJObj = getJSONFromUrl(URL+event_id, Requests.GET);
        int dummy_count = 0;
        if (resultJObj != null) {
            Log.d("FETCH ATTENDEES", resultJObj.toString());
            try {
                JSONArray array = resultJObj.getJSONArray("user_mappings");
                String macAddress, username, name, gcm_id, photo_url;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.optJSONObject(i);
                    if(item != null) {
                        macAddress = item.optString("mac_address");
                        macAddress = macAddress.equals("") ? dummy_count++ + "" : macAddress;
                        username = item.optString("user_name");
                        name = item.optString("name");
                        gcm_id = item.optString("gcm_id");
                        photo_url = item.optString("photo_url");
                        Attendee attendee = new Attendee(macAddress, username, name, gcm_id, photo_url);
                        attendees.put(macAddress, attendee);
                    }
                }
            } catch (JSONException e) {
            }
        }

        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        if (offline) {
            new AlertFragment("No Connection", "Attendee list cannot be updated").show(activity.getFragmentManager(), "Alert");
        } else {
            Intent intent = new Intent();
            intent.setAction(Action.UPDATE_ATTENDEE_FRAGMENT_ADAPTER);
            activity.sendBroadcast(intent);
        }
    }

}
