package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.util.Log;

import org.json.JSONObject;

import g1436218.com.spyder.config.GlobalConfiguration;

/**
 * Created by Cherie on 11/25/2014.
 */
public class UnlinkDevice extends BaseAsyncTask {

    private static String TAG = "UnlinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";

    private String username;
    private String password;

    public UnlinkDevice(Activity activity, String username, String password) {
        super(activity);
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground" + username + password);
        //JSONObject jsonObject = getJSONFromUrl(URL + "?username=" + username + "&password=" + password, Responses.DELETE);

        return null;
    }
}
