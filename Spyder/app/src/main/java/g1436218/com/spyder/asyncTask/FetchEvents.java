package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.dialogFragment.AlertFragment;
import g1436218.com.spyder.fragment.EventListFragment;
import g1436218.com.spyder.object.Action;


public class FetchEvents extends BaseMainAsyncTask {

    private EventListFragment fragment;
    private String keyword;
    private String url;

    public FetchEvents(EventListFragment fragment, String keyword) {
        super(fragment.getMainActivity());
        this.fragment = fragment;
        this.keyword = keyword;
        this.url = GlobalConfiguration.DEFAULT_URL + "searchEvent";
    }

    @Override
    public void onPreExecute() {
        broadcastClearAdapter();
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {

        String username = activity.getSharedPrefString("Username");

        addToParams("user_name", username);
        addToParams("event_search_string", keyword);

        result = getStringFromUrl(url, Requests.POST);
        Log.i("FECTHEVENT", result);

        if (result != null) {
            try {
                JSONArray array = toJSONArray(result);
                if (array == null) {
                    return null;
                }
                String status, event_id, event_name;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    status = item.optString("status");
                    event_id = item.optString("event_id");
                    event_name = item.optString("event_name");
                    broadcastAddItem(status, event_id, event_name);
                }
            } catch (JSONException e) {
                e.getMessage();
            }
        }

        return null;

    }

    @Override
    public void onPostExecute(Void v) {
        if (offline) {
            new AlertFragment("No Connection", "Event list cannot be updated").show(activity.getFragmentManager(), "Alert");
        }
    }

    private void broadcastAddItem(String status, String event_id, String event_name) {
        Intent intent = new Intent();
        intent.setAction(Action.EVENT_ADAPTER_ADD_ITEM);
        intent.putExtra("status", status);
        intent.putExtra("event_id", event_id);
        intent.putExtra("event_name", event_name);
        activity.sendBroadcast(intent);
    }

    private void broadcastClearAdapter() {
        Intent intent = new Intent();
        intent.setAction(Action.EVENT_ADAPTER_CLEAR);
        activity.sendBroadcast(intent);
    }

}
