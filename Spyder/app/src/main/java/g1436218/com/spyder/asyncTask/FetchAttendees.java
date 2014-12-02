package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.UserMap;

public class FetchAttendees extends BaseMainAsyncTask {

    private final String TAG = "FetchAttendeeList";
    private final String URL = GlobalConfiguration.DEFAULT_URL + "eventUsers?event_id=" + GlobalConfiguration.EVENT_ID;

    private UserMap userMap;

    public FetchAttendees(MainActivity activity) {
        super(activity);
        this.userMap = UserMap.getInstance();
    }

    @Override
    protected Void doInBackground(Void... params) {

        /* Dummy response since the API is not ready; */
        //result = "{\"user_mappings\":[{\"mac_address\":\"38:2D:D1:1B:09:2A\",\"user_name\":\"GalaxyTab4\"},{\"mac_address\":\"F0:E7:7E:52:57:3E\",\"user_name\":\"GT-N7000\"},{\"mac_address\":\"48:74:6E:75:64:75\",\"user_name\":\"iPhone\"}]}";
        //resultJObj = toJSONObject(result);
        resultJObj = getJSONFromUrl(URL, Responses.GET);

        activity.clearAttendees();
        userMap.clear();
        userMap.put("48:74:6E:75:64:75", "iPhone");

        if (resultJObj != null) {
            try {
                JSONArray array = resultJObj.getJSONArray("user_mappings");
                String macAddress, username;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    macAddress = item.getString("mac_address");
                    username = item.getString("user_name");
                    activity.addAttendee(new Attendee(macAddress, username));
                    userMap.put(macAddress, username);
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
        Log.i(TAG, userMap.toString());
    }

}