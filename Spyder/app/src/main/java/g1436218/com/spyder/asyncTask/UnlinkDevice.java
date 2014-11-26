package g1436218.com.spyder.asyncTask;

import android.app.Activity;

import g1436218.com.spyder.config.GlobalConfiguration;

/**
 * Created by Cherie on 11/25/2014.
 */
public class UnlinkDevice extends BaseAsyncTask {

    private static String TAG = "LinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";
    public UnlinkDevice(Activity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
