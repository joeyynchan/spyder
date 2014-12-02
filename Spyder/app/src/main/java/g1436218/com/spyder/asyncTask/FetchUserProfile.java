package g1436218.com.spyder.asyncTask;

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


    private static String URL = GlobalConfiguration.DEFAULT_URL + "user/profile?user_id=";
    private static String TAG = "FetchUserProfile";

    private String username;
    private User user;

    public FetchUserProfile(MainActivity activity, String username) {
        super(activity);
        this.username = username;
        this.URL += username;
        Log.d(TAG, username);
    }

    @Override
    public void onPreExecute() {
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
        JSONObject jsonObject = getJSONFromUrl(URL, Responses.GET);

        if(statusCode == 204){
            //notFound
        }else if(statusCode == 200){
            Log.d(TAG, jsonObject.toString());
            insertDataToUser(jsonObject);
        }
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        ProfileFragment profileFragment = new ProfileFragment(activity, user);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, profileFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void insertDataToUser(JSONObject jsonObject){
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
    private void defaultDataToUser(){
        user.setCompany("");
        user.setEmail("");
        user.setExternal_link("");
        user.setGender("");
        user.setName("");
        user.setOccupation("");
        user.setPhone("");
    }
}
