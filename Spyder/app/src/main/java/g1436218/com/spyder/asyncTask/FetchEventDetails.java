package g1436218.com.spyder.asyncTask;

import android.app.FragmentTransaction;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.fragment.EventFragment;
import g1436218.com.spyder.object.Event;

/**
 * Created by Cherie on 12/4/2014.
 */
public class FetchEventDetails extends BaseMainAsyncTask{

    protected String URL = GlobalConfiguration.DEFAULT_URL + "readEvent?event_id=";
    private static String TAG = "FetchEventDetails";

    protected Event event;

    public FetchEventDetails(MainActivity activity, Event event) {
        super(activity);
        this.event = event;
        this.URL += event.getId();
    }
    @Override
    protected Void doInBackgroundOffline(Void... params) {

        return null;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {
        Log.d(TAG, URL);
        JSONObject jsonObject = getJSONFromUrl(URL, Responses.GET);
        Log.d(TAG, statusCode + "");
        if(statusCode == 200){
            Log.d(TAG, jsonObject.toString());
            insertDataToEvent(jsonObject);
        }else{
            //notFound
            defaultDataToEvent();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v){
        EventFragment eventFragment = new EventFragment(activity, event);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void defaultDataToEvent() {

    }

    private void insertDataToEvent(JSONObject jsonObject) {
        try {
            event.setName(jsonObject.has("name") ? jsonObject.getString("name") : "");
            event.setStatus(jsonObject.has("status") ? jsonObject.getString("status") : "");
            event.setStartTime(jsonObject.has("start_time") ? jsonObject.getString("start_time") : "");
            event.setEndTime(jsonObject.has("end_time") ? jsonObject.getString("end_time") : "");
            event.setLocation(jsonObject.has("address") ? jsonObject.getString("address") : "");
        } catch (JSONException e) {
            Log.d(TAG, "JSONException");
        }

    }
}
