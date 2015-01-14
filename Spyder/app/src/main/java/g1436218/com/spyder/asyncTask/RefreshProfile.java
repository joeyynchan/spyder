package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

public class RefreshProfile extends FetchProfile {

    public RefreshProfile(MainActivity activity) {
        super(activity, "");
        this.username = activity.getSharedPrefString("Username");
    }

    @Override
    protected void onPostExecute(Void v){
        activity.putSharedPrefString("name", user.getName());
        activity.putSharedPrefString("company", user.getCompany());
        activity.putSharedPrefString("email", user.getEmail());
        activity.putSharedPrefString("link", user.getExternal_link());
        activity.putSharedPrefString("gender", user.getGender());
        activity.putSharedPrefString("occupation", user.getGender());
        activity.putSharedPrefString("photourl", user.getPhotoURL());
        activity.putSharedPrefString("phone", user.getPhone());
    }
}
