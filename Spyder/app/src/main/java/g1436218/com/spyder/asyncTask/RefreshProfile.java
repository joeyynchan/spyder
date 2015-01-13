package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

/**
 * Created by Cherie on 1/13/2015.
 */
public class RefreshProfile extends FetchProfile {
    SharedPreferences sharedPref;

    public RefreshProfile(MainActivity activity) {
        super(activity, "");
        sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.username = sharedPref.getString(activity.getString(R.string.username), "");
    }

    @Override
    protected void onPostExecute(Void v){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", user.getName());
        editor.putString("company", user.getCompany());
        editor.putString("email", user.getEmail());
        editor.putString("link", user.getExternal_link());
        editor.putString("gender", user.getGender());
        editor.putString("occupation", user.getOccupation());
        editor.putString("photourl", user.getPhotoURL());
        editor.putString("phone", user.getPhone());
        editor.commit();
    }
}
