package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.Interactions;

public class SubmitBluetoothData extends BaseMainAsyncTask{

    private static String TAG = "SubmitBluetoothData";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "submit_data";
    private InteractionPackage interactionPackage;

    public SubmitBluetoothData(MainActivity activity) {
        super(activity);
        this.interactionPackage = activity.getInteractionPackage();
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {

        String username = activity.getSharedPrefString(SharedPref.USERNAME);
        String event_id = activity.getSharedPrefString(SharedPref.EVENT_ID);

        addToParams("user_name", username);
        addToParams("event_id", event_id);
        addToParams("time_interval", Integer.toString(GlobalConfiguration.BLUETOOTH_TIME_INTERVAL));
        addToParams("data", convertListToJSONArray());

        Log.i(TAG, this.params.toString());

        JSONObject obj = getJSONFromUrl(URL, Requests.POST);
        Log.i(TAG, statusCode+"");
        Log.i(TAG, obj.toString());
        return null;
    }

    private JSONArray convertListToJSONArray() {
        Iterator<Interactions> iterator = interactionPackage.iterator();
        JSONArray array = new JSONArray();
        while(iterator.hasNext()) {
            Interactions interactions = iterator.next();
            JSONArray subArray = convertSetToJSONArray(interactions);
            array.put(subArray);
        }
        return array;
    }

    private JSONArray convertSetToJSONArray(HashSet<Interaction> interactions) {
        Iterator<Interaction> iterator = interactions.iterator();
        JSONArray array = new JSONArray();
        while(iterator.hasNext()) {
            Interaction interaction = iterator.next();
            JSONObject obj = new JSONObject();
            try {
                obj.put("user_name", interaction.getUsername());
                obj.put("strength", interaction.getStrength());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        return array;
    }

    @Override
    public void onPostExecute(Void v){
        interactionPackage.clear();
    }
}
