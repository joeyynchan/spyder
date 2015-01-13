package g1436218.com.spyder.asyncTask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.dialogFragment.AlertFragment;
import g1436218.com.spyder.fragment.ProfileFragment;
import g1436218.com.spyder.object.User;

public class DisplayAttendeeProfile extends FetchProfile {


    protected String URL = GlobalConfiguration.DEFAULT_URL + "user/profile?user_name=";
    private static String TAG = "FetchUserProfile";

    public DisplayAttendeeProfile(MainActivity activity, String username) {
        super(activity, username);
    }

    @Override
    protected void onPostExecute(Void v) {
        if (offline) {
            new AlertFragment("No Connection", "User Profile cannot be fetched").show(activity.getFragmentManager(), "Alert");
            return;
        }
        ProfileFragment eventFragment = new ProfileFragment(activity, user);
        Log.d(TAG, user.getName());
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
