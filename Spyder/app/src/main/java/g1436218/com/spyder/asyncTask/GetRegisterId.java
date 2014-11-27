package g1436218.com.spyder.asyncTask;

import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

public class GetRegisterId extends BaseMainAsyncTask {

    public GetRegisterId(MainActivity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity);
        String regid = "";
        try {
            regid = gcm.register(GlobalConfiguration.PROJECT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("GCM", "Device registered, registration ID=" + regid);
        return null;
    }

}
