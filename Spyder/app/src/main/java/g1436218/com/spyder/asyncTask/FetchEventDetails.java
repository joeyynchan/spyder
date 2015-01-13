package g1436218.com.spyder.asyncTask;

import android.app.FragmentTransaction;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.dialogFragment.AlertFragment;
import g1436218.com.spyder.fragment.EventFragment;
import g1436218.com.spyder.object.Event;

public class FetchEventDetails extends BaseMainAsyncTask{

    protected String URL = GlobalConfiguration.DEFAULT_URL + "event_data?event_id=";
    private static String TAG = "FetchEventDetails";

    protected Event event;

    public FetchEventDetails(MainActivity activity, Event event) {
        super(activity);
        this.event = event;

    }
    @Override
    protected Void doInBackgroundOffline(Void... params) {

        return null;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {
        URL += event.getId();
        Log.d(TAG, URL);

        JSONObject jsonObject = getJSONFromUrl(URL, Requests.GET);
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
        if (offline) {
            new AlertFragment("No Connection", "Attendee list cannot be updated").show(activity.getFragmentManager(), "Alert");
            return;
        }
        EventFragment eventFragment = new EventFragment(activity, event);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void defaultDataToEvent() {

    }

    private void insertDataToEvent(JSONObject jsonObject) {
        event.setName(jsonObject.optString("name"));
        event.setStartTime(jsonObject.optString("start_time"));
        event.setEndTime(jsonObject.optString("end_time"));
        event.setLocation(jsonObject.optString("address"));

    }
}
