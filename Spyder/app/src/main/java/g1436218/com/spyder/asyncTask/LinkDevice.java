package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.fragment.LoginFragment;

import static android.bluetooth.BluetoothAdapter.getDefaultAdapter;

/**
 * Created by Cherie on 11/9/2014.
 */
public class LinkDevice extends BaseAsyncTask{

    private static String TAG = "LinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";

    private String username;
    private String password;

    public LinkDevice(Activity activity, String username, String password) {
        super(activity);
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... params) {
        addToParams("user_name", username);
        addToParams("password", password);
        addToParams("mac_address", getDefaultAdapter().getAddress());

        JSONObject jsonObject = getJSONFromUrl(URL, Responses.POST);
        Log.d(TAG, this.statusCode + "");

        return null;
    }

    //Start MainActivity if login succeed
    @Override
    public void onPostExecute(Void v) {
        Log.d(TAG, "onPostExecute");
        switch(this.statusCode) {
            case (200):{
                setErrMsgToNotFound();
                gotoMainActivity();
                }
                break;

            case (201):{
                setErrMsgToNotFound();
                gotoMainActivity();
                }
                break;

            case (404):
                setErrMsgToNotFound();
                break;

            case (409):
                setErrMsgToNotFound();
                break;

            default:
                setErrMsgToNotFound();
                break;
        }
    }

    private void gotoMainActivity(){
        Context context = activity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.username), username);
        editor.putString(context.getString(R.string.password), password);
        editor.commit();
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    private void setErrMsgToNotFound(){
        TextView login_text_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_login_errmsg);
        switch(this.statusCode) {
            case(200): login_text_errmsg.setText("Login successfully\n");
                break;
            case(201): login_text_errmsg.setText("Successfully linked device\n");
                break;
            case(404): login_text_errmsg.setText("Username/Password not match\n");
                break;
            case(409): login_text_errmsg.setText("User is already linked with other device\n");
                break;
            default: login_text_errmsg.setText("No response from server, please try again later\n");
                break;

        }
    }

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
}


