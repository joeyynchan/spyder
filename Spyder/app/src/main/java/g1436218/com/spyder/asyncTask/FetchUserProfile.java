package g1436218.com.spyder.asyncTask;

import android.app.FragmentTransaction;

import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.fragment.UserFragment;
import g1436218.com.spyder.object.User;

public class FetchUserProfile extends BaseMainAsyncTask {


    private static String URL = GlobalConfiguration.DEFAULT_URL + "user/profile?user_id=";

    private String username;
    private User user;

    public FetchUserProfile(MainActivity activity, String username) {
        super(activity);
        this.username = username;
        this.URL += username;
    }

    @Override
    public void onPreExecute() {
        user = new User(username);
    }

    @Override
    protected Void doInBackgroundOffline(Void... params) {
        return null;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        UserFragment eventFragment = new UserFragment(activity, user);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
