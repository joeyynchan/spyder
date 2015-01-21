package g1436218.com.spyder.asyncTask;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.dialogFragment.AlertFragment;
import g1436218.com.spyder.fragment.ProfileFragment;

public class DisplayUserProfile extends RefreshProfile {

    public DisplayUserProfile(MainActivity activity) {
        super(activity);
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
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
