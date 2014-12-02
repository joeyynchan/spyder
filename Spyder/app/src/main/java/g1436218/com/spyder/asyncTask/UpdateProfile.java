package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.User;

public class UpdateProfile extends BaseMainAsyncTask {

    private User user;
    private String url;

    public UpdateProfile(MainActivity activity, User user) {
        super(activity);

        Context context = activity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(context.getString(R.string.username), "");

        this.user = user;
        this.url = GlobalConfiguration.DEFAULT_URL + "user/profile?user_id=" + username;
    }

    @Override
    protected Void doInBackground(Void... params) {

        addToParams("name", user.getName());
        addToParams("job", user.getOccupation());
        addToParams("company", user.getCompany());
        addToParams("photo", "");
        addToParams("email", user.getEmail());
        addToParams("phone", user.getPhone());
        addToParams("external_link", user.getExternal_link());
        addToParams("gender", user.getGender());
        addToParams("connectoins", "");

        getJSONFromUrl(url, Responses.PUT);
        Log.i("UpdateProfile", statusCode + "");
        return null;
    }

}