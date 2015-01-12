package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.util.Log;
import org.json.JSONObject;

import g1436218.com.spyder.config.GlobalConfiguration;

public class UnlinkDevice extends BaseAsyncTask {

    private static String TAG = "UnlinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "UnlinkUser";

    private String username;
    private String password;

    public UnlinkDevice(Activity activity, String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... params) {
        addToParams("user_name", username);
        addToParams("password", password);

        JSONObject jsonObject = getJSONFromUrl(URL, Requests.POST);
        Log.d(TAG, this.statusCode + "");

        //TODO : This is not working
        return null;
    }
}
