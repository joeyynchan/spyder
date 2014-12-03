package g1436218.com.spyder.asyncTask;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.fragment.ProfileFragment;
import g1436218.com.spyder.object.User;

public class DisplayProfile extends BaseMainAsyncTask {

    private User user;

    public DisplayProfile(MainActivity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackgroundOffline(Void... params) {

        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(activity.getString(R.string.username), "");

       user = new User(username);
        return null;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {
        user = new User("offline");
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        ProfileFragment profileFragment = new ProfileFragment(activity, user);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, profileFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
    }
}
