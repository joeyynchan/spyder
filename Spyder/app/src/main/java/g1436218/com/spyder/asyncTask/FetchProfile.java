package g1436218.com.spyder.asyncTask;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.User;

/**
 * Created by Cherie on 1/13/2015.
 */
public class FetchProfile extends BaseMainAsyncTask{
    protected String URL = GlobalConfiguration.DEFAULT_URL + "user/profile?user_name=";
    private static String TAG = "FetchProfile";

    protected String username;
    protected User user;

    public FetchProfile(MainActivity activity, String username) {
        super(activity);
        this.username = username;
    }

    @Override
    protected void onPreExecute() {
        this.URL += username;
        user = new User(username);
    }

    @Override
    protected Void doInBackgroundOnline(Void... params) {
        Log.d(TAG, URL);
        JSONObject jsonObject = getJSONFromUrl(URL, Requests.GET);
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

    /* get profile detail from JSONObject to Profile fragment */
    protected void insertDataToUser(JSONObject jsonObject){
        Log.d(TAG, "insertDataToUser");
        user.setCompany(jsonObject.optString("company"));
        user.setEmail(jsonObject.optString("email"));
        user.setExternal_link(jsonObject.optString("external_link"));
        user.setGender(jsonObject.optString("gender"));
        user.setName(jsonObject.optString("name"));
        Log.d(TAG, jsonObject.optString("name"));
        user.setOccupation(jsonObject.optString("job"));
        user.setPhone(jsonObject.optString("phone"));
        user.setPhotoURL(jsonObject.optString("photo"));

        if(user.getPhotoURL() != null){
            Bitmap bitmap = null;
            try {
                bitmap = getImageFromURL(new java.net.URL(user.getPhotoURL()));
            } catch (MalformedURLException e) {
            }
            user.setPhoto(bitmap);
        }
    }

    /* set texts to empty string in case of profile not found */
    protected void defaultDataToUser(){
        Log.d(TAG, "defaultDataToUser");
        user.setCompany("");
        user.setEmail("");
        user.setExternal_link("");
        user.setGender("");
        user.setName("");
        user.setOccupation("");
        user.setPhone("");
    }
}
