package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.config.SharedPref;

public class RefreshProfile extends FetchProfile {

    public RefreshProfile(MainActivity activity) {
        super(activity, "");
        this.username = activity.getSharedPrefString(SharedPref.USERNAME);
    }

    @Override
    protected void onPostExecute(Void v){
        activity.putSharedPrefString(SharedPref.NAME, user.getName());
        activity.putSharedPrefString(SharedPref.COMPANY, user.getCompany());
        activity.putSharedPrefString(SharedPref.EMAIL, user.getEmail());
        activity.putSharedPrefString(SharedPref.LINK, user.getExternal_link());
        activity.putSharedPrefString(SharedPref.GENDER, user.getGender());
        activity.putSharedPrefString(SharedPref.OCCUPATION, user.getOccupation());
        activity.putSharedPrefString(SharedPref.PHOTO_URL, user.getPhotoURL());
        activity.putSharedPrefString(SharedPref.PHONE, user.getPhone());
    }
}
