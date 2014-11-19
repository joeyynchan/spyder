package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.object.Connection;

/**
 * Created by Cherie on 11/19/2014.
 */
public class SubmitBluetoothData extends BaseAsyncTask{

    private static String TAG = "SubmitBluetoothData";
    private static String URL = "http://146.169.46.38:8080/MongoDBWebapp/submit_data?user_name=Gun&event_id=5463faffe4b0c72e310cff42";
    private static int TIME_INTERVAL = 15;
    private HashSet<Connection> connections;

    public SubmitBluetoothData(HashSet<Connection> connections) {
        super();
        this.connections = connections;
    }


    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground");
        addToParams("time_interval", Integer.toString(TIME_INTERVAL));
        addToParams("data", convertListToJSONArray());
        Log.d(TAG, convertListToJSONArray().toString());
        Log.d(TAG, this.params.toString());
        return null;
    }

    private JSONArray convertListToJSONArray() {
        Iterator<Connection> iterator = connections.iterator();
        JSONArray array = new JSONArray();
        while(iterator.hasNext()) {
            Connection conn = iterator.next();
            array.put(conn.getUsername() + "," + conn.getStrength());
        }
        array.put("Joey,38:2D:D1:1B:09:2A");
        return array;
    }

    @Override
    public void onPostExecute(Void v){
        Log.d(TAG, "onPostExecute");
    }
}
