package g1436218.com.spyder.asyncTask;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.Interaction;

/**
 * Created by Cherie on 11/19/2014.
 */
public class SubmitBluetoothData extends BaseAsyncTask{

    private static String TAG = "SubmitBluetoothData";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "submit_data?user_name=Gun&event_id=5463faffe4b0c72e310cff42";
    private ArrayList<HashSet<Interaction>> interactionsArray;

    public SubmitBluetoothData(ArrayList<HashSet<Interaction>> interactionsArray) {
        super();
        this.interactionsArray = interactionsArray;
    }


    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground");
        addToParams("time_interval", Integer.toString(GlobalConfiguration.BLUETOOTH_TIME_INTERVAL));
        addToParams("data", convertListToJSONArray());

        Log.d(TAG, this.params.toString());
        JSONObject obj = getJSONFromUrl(URL);
        Log.d(TAG, obj.toString());
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
        Log.d(TAG, "onPostExecute");
    }
}
