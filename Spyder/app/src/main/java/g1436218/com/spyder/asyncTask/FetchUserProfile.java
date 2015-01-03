package g1436218.com.spyder.asyncTask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.fragment.ProfileFragment;
import g1436218.com.spyder.object.User;

public class FetchUserProfile extends BaseMainAsyncTask {


    protected String URL = GlobalConfiguration.DEFAULT_URL + "user/profile?user_name=";
    private static String TAG = "FetchUserProfile";

    protected String username;
    protected User user;

    public FetchUserProfile(MainActivity activity, String username) {
        super(activity);
        this.username = username;
        this.URL += username;
    }

    @Override
    protected void onPreExecute() {
        user = new User(username);
    }

    @Override
    protected Void doInBackgroundOffline(Void... params) {
        user.setName("OFF");
        user.setGender("M");
        user.setCompany("Offline Ltd");
        user.setOccupation("CEO");
        user.setPhone("+44 7392729371");
        user.setEmail("offline@offline.com");
        user.setExternal_link("www.offline.com");
        return null;
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {
        Log.d(TAG, URL);
        JSONObject jsonObject = getJSONFromUrl(URL, Responses.GET);
        Log.d(TAG, statusCode + "");
        if(statusCode == 200){
            Log.d(TAG, jsonObject.toString());
            insertDataToUser(jsonObject);
        }else{
            //notFound
            defaultDataToUser();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        ProfileFragment eventFragment = new ProfileFragment(activity, user);
        if (!(fragment instanceof ProfileFragment)) {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /* get profile detail from JSONObject to Profile fragment */
    protected void insertDataToUser(JSONObject jsonObject){
        try {
            user.setCompany(jsonObject.getString("company"));
            user.setEmail(jsonObject.getString("email"));
            user.setExternal_link(jsonObject.getString("external_link"));
            user.setGender(jsonObject.getString("gender"));
            user.setName(jsonObject.getString("name"));
            user.setOccupation(jsonObject.getString("job"));
            user.setPhone(jsonObject.getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* set texts to empty string in case of profile not found */
    protected void defaultDataToUser(){
        user.setCompany("");
        user.setEmail("");
        user.setExternal_link("");
        user.setGender("");
        user.setName("");
        user.setOccupation("");
        user.setPhone("");
    }
}
