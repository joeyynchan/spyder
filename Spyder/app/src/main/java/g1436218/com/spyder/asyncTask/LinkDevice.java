package g1436218.com.spyder.asyncTask;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.io.IOException;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.dialogFragment.UnlinkFragment;
import g1436218.com.spyder.object.Action;

import static android.bluetooth.BluetoothAdapter.getDefaultAdapter;

public class LinkDevice extends BaseLoginAsyncTask{

    private static String TAG = "LinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";

    private String username;
    private String password;
    private String macAddress;
    private String gcmID;

    public LinkDevice(LoginActivity activity, String username, String password) {
        super(activity);
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... params) {

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity);
        gcmID = "";
        try {
            gcmID = gcm.register(GlobalConfiguration.PROJECT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        macAddress = getDefaultAdapter().getAddress();

        addToParams("user_name", username);
        addToParams("password", password);
        addToParams("mac_address", macAddress);
        addToParams("gcm_id", gcmID);

        Log.i("GCM", "GCM ID=" + gcmID);

        Intent intent = new Intent();
        intent.setAction(Action.GET_GCM);
        intent.putExtra("GCMID", gcmID);
        activity.sendBroadcast(intent);

        if (username.equals(GlobalConfiguration.OFFLINE_MODE)) {
            statusCode = 999;
        } else {
            JSONObject jsonObject = getJSONFromUrl(URL, Requests.POST);
        }
        Log.i(TAG, "Login Attempt: " + username + ":" + password + " ---------- Result:" + statusCode);
        return null;
    }

    //Start MainActivity if login succeed
    @Override
    public void onPostExecute(Void v) {
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
                showUnlink();
                break;

            case (999):
                gotoMainActivity();

            default:
                setErrMsgToNotFound();
                break;
        }
    }

    private void gotoMainActivity(){
        /* insert (username, password) into sharedPreference */
        Context context = activity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.username), username);
        editor.putString(context.getString(R.string.password), password);
        editor.putString("macAddress", macAddress);
        editor.putString("gcmID", gcmID);

        editor.putBoolean(GlobalConfiguration.OFFLINE_MODE, username.equals(GlobalConfiguration.OFFLINE_MODE));
        editor.commit();
        /* start mainActivity */
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void setErrMsgToNotFound(){
        TextView login_text_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_login_errmsg);
        if(login_text_errmsg != null) {
            switch (this.statusCode) {
                case (200):
                    login_text_errmsg.setText("Login successfully\n");
                    break;
                case (201):
                    login_text_errmsg.setText("Successfully linked device\n");
                    break;
                case (404):
                    login_text_errmsg.setText("Username/Password not match\n");
                    break;
                case (409):
                    login_text_errmsg.setText("User is already linked with other device\n");
                    break;
                case 410: {
                    login_text_errmsg.setText("Username cannot be empty");
                    break;
                }
                case 411: {
                    login_text_errmsg.setText("Password cannot be empty");
                    break;
                }
                case 412: {
                    login_text_errmsg.setText("This device is used by antoher user.");
                    break;
                }
                default:
                    login_text_errmsg.setText("No response from server, please try again later\n");
                    break;

            }
        }
    }

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }

    private void showUnlink() {
                /* insert (username, password) into sharedPreference */
        Context context = activity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.username), username);
        editor.putString(context.getString(R.string.password), password);
        editor.commit();
        FragmentManager fragmentManager = activity.getFragmentManager();
        new UnlinkFragment().show(fragmentManager, "Unlink");
        TextView textview_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_login_errmsg);

        textview_errmsg.setText("");
    }
}

