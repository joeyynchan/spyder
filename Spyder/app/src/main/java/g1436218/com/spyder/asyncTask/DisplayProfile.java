package g1436218.com.spyder.asyncTask;

import android.app.FragmentTransaction;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.fragment.ProfileFragment;
import g1436218.com.spyder.fragment.UserFragment;
import g1436218.com.spyder.object.User;

public class DisplayProfile extends BaseMainAsyncTask {

    private User user;

    public DisplayProfile(MainActivity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
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
