package g1436218.com.spyder.asyncTask;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.Interaction;

public class SubmitBluetoothData extends BaseAsyncTask{

    private static String TAG = "SubmitBluetoothData";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "submit_data?user_name=cherrie&event_id=54751551e4b08c8af4a64db3";
    private ArrayList<HashSet<Interaction>> interactionsArray;
    private MainActivity activity;

    public SubmitBluetoothData(MainActivity activity, ArrayList<HashSet<Interaction>> interactionsArray) {
        super();
        this.activity = activity;
        this.interactionsArray = interactionsArray;
    }


    @Override
    protected Void doInBackground(Void... params) {
        addToParams("time_interval", Integer.toString(GlobalConfiguration.BLUETOOTH_TIME_INTERVAL));
        addToParams("data", convertListToJSONArray());

        Log.i(TAG, this.params.toString());

        JSONObject obj = getJSONFromUrl(URL, Responses.POST);
        return null;
    }

    private JSONArray convertListToJSONArray() {
        Iterator<HashSet<Interaction>> iterator = interactionsArray.iterator();
        JSONArray array = new JSONArray();
        while(iterator.hasNext()) {
            HashSet<Interaction> interactions = iterator.next();
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
        activity.clearArray();
    }
}
