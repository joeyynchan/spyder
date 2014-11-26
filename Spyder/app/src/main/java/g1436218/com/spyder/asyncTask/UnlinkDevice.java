package g1436218.com.spyder.asyncTask;

import android.app.Activity;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

/**
 * Created by Cherie on 11/25/2014.
 */
public class UnlinkDevice extends BaseMainAsyncTask {

    private static String TAG = "LinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";

    public UnlinkDevice(MainActivity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
