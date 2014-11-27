package g1436218.com.spyder.asyncTask;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

public class JoinEvent extends BaseMainAsyncTask {

    private static String TAG = "JoinEvent";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "event/join?event_id=";

    String eventId;

    public JoinEvent (MainActivity activity, String eventId) {
        super(activity);
        this.eventId = eventId;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Context context = activity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(context.getString(R.string.username), "");

        addToParams("user_name", username);
        addToParams("status", "Attending");

        JSONObject jsonObject = getJSONFromUrl(URL+GlobalConfiguration.EVENT_ID, Responses.POST);
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        switch(statusCode) {
            case 200: break;
            default: break;
        }
        Log.i(TAG, statusCode + "");
    }
}
