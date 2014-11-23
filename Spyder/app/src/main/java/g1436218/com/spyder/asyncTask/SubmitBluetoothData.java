package g1436218.com.spyder.asyncTask;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.object.Interaction;

/**
 * Created by Cherie on 11/19/2014.
 */
public class SubmitBluetoothData extends BaseAsyncTask{

    private static String TAG = "SubmitBluetoothData";
    private static String URL = "http://146.169.46.38:8080/MongoDBWebapp/submit_data?user_name=Gun&event_id=5463faffe4b0c72e310cff42";
    private static int TIME_INTERVAL = 15;
    private HashSet<Interaction> connections;

    public SubmitBluetoothData(HashSet<Interaction> connections) {
        super();
        this.connections = connections;
    }


    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground");
        addToParams("time_interval", Integer.toString(TIME_INTERVAL));
        addToParams("data", convertListToJSONArray());

        Log.d(TAG, this.params.toString());
        JSONObject obj = getJSONFromUrl(URL);
        Log.d(TAG, obj.toString());
        return null;
    }

    private JSONArray convertListToJSONArray() {
        Iterator<Interaction> iterator = connections.iterator();
        JSONArray array = new JSONArray();
        while(iterator.hasNext()) {
            Interaction conn = iterator.next();
            JSONObject obj = new JSONObject();
            try {
                obj.put("user_name", conn.getUsername());
                obj.put("strength", conn.getStrength());
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
