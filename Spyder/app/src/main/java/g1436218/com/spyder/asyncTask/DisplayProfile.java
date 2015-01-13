package g1436218.com.spyder.asyncTask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.dialogFragment.AlertFragment;
import g1436218.com.spyder.fragment.ProfileFragment;

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
        if (offline) {
            new AlertFragment("No Connection", "User Profile cannot be fetched").show(activity.getFragmentManager(), "Alert");
            return;
        }
        ProfileFragment eventFragment = new ProfileFragment(activity, user);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
    }
}
