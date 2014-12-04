package g1436218.com.spyder.asyncTask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.fragment.ProfileFragment;
import g1436218.com.spyder.object.User;

public class DisplayProfile extends FetchUserProfile {


    public DisplayProfile(MainActivity activity) {
        super(activity, "");
        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(activity.getString(R.string.username), "");
        this.username = username;
        this.URL += username;
    }

    @Override
    protected void onPostExecute(Void v) {
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        ProfileFragment eventFragment = new ProfileFragment(activity, user);
        if (!(fragment instanceof ProfileFragment)) {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
            fragmentTransaction.commit();
        }
    }
}
