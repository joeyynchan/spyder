package g1436218.com.spyder.asyncTask;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.dialogFragment.AlertFragment;
import g1436218.com.spyder.fragment.EventFragment;

public class JoinEvent extends BaseMainAsyncTask {

    private static String TAG = "JoinEvent";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "event/join?event_id=";

    EventFragment fragment;
    String eventId;

    public JoinEvent (EventFragment fragment, String eventId) {
        super(fragment.getMainActivity());
        this.fragment = fragment;
        this.eventId = eventId;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {

        String username = activity.getSharedPrefString(SharedPref.USERNAME);

        addToParams("user_name", username);
        addToParams("status", "Attending");

        JSONObject jsonObject = getJSONFromUrl(URL+eventId, Requests.POST);
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        if (offline) {
            new AlertFragment("No Connection", "Cannot Join Event!").show(activity.getFragmentManager(), "Alert");
            return;
        }
        switch(statusCode) {
            case 200: {
                joinedEvent();
                break;
            }
            case 403: {
                joinedEvent();
                break;
            }
            default: break;
        }
        Log.i(TAG, statusCode + "");
    }

    private void joinedEvent() {
        Button button_joinEvent = (Button) activity.findViewById(R.id.button_fragment_event_joinEvent);
        button_joinEvent.setClickable(false);
        button_joinEvent.setText("Attending Event");
        button_joinEvent.setVisibility(View.GONE);

        Button button_setToCurrentEvent = (Button)  activity.findViewById(R.id.button_fragment_event_setToCurrentEvent);
        button_setToCurrentEvent.setVisibility(View.VISIBLE);
    }
}
