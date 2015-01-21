package g1436218.com.spyder.asyncTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.dialogFragment.AlertFragment;

public abstract class BaseMainAsyncTask extends BaseAsyncTask {

    private static final String TAG = "Session Token";
    protected MainActivity activity;
    protected ProgressDialog dialog;
    protected boolean offline = false;

    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";
    private String username;
    private String password;
    private String macAddress;
    private String gcmID;
    private boolean logout;

    protected abstract Void doInBackgroundOnline(Void... params);

    public BaseMainAsyncTask(MainActivity activity) {
        super();
        this.activity = activity;
        logout = false;
    }

    @Override
    protected Void doInBackground(Void... params) {

        boolean isOffline = false;
        try {
            SocketAddress socketAddress = new InetSocketAddress(GlobalConfiguration.IP, GlobalConfiguration.PORT);
            Socket socket = new Socket();

            int timeoutMs = 2000;
            socket.connect(socketAddress, timeoutMs);
        } catch (IOException e) {
            isOffline = true;
        }

        if (isOffline) {
            return doInBackgroundOffline();
        }

        username = activity.getSharedPrefString(SharedPref.USERNAME);
        password = activity.getSharedPrefString(SharedPref.PASSWORD);
        macAddress = activity.getSharedPrefString(SharedPref.MAC_ADDRESS);
        gcmID = activity.getSharedPrefString(SharedPref.GCM_ID);

        addToParams("user_name", username);
        addToParams("password", password);
        addToParams("mac_address", macAddress);
        addToParams("gcm_id", gcmID);

        JSONObject jsonObject = getJSONFromUrl(URL, Requests.POST);
        Log.d(TAG, statusCode + "");
        if(statusCode != 200){
            // Was unlinked by another device
            Log.d(TAG, "Mac address confliction");
            logout = true;

            Log.d(TAG, "Logging out");
            if(logout){
                /* clear anything that was stored in sharedPref */
                activity.clearSharedPref();
                new AlertFragment("Logging out", username + "has logged in on another device.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* Return to Login Screen */
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }).show(activity.getFragmentManager(), "Alert");
            }
            return null;
        }
        return doInBackgroundOnline();
    }

    protected  Void doInBackgroundOffline(Void... params) {
        offline = true;
        return null;
    }

    @Override
    protected void onPostExecute(Void v){


    }

    protected void showProgressDialog() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Please wait...");
        dialog.show();
    }

    protected void hideProgressDialog() {
        dialog.hide();
    }

}
