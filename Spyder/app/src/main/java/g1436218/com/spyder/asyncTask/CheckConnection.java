package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import g1436218.com.spyder.R;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.dialogFragment.AlertFragment;

public class CheckConnection extends BaseAsyncTask {

    private Activity activity;
    private static String TAG = "CheckConnection";

    public CheckConnection(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        boolean live = false;
        try {
            SocketAddress socketAddress = new InetSocketAddress(GlobalConfiguration.IP, GlobalConfiguration.PORT);
            Socket socket = new Socket();

            int timeoutMs = 2000;
            socket.connect(socketAddress, timeoutMs);
            Log.i("SERVER_STATUS", "ONLINE");

        } catch (IOException e) {
            Log.i("SERVER_STATUS", "OFFLINE");
            new AlertFragment("No Connection", "Server is not running at the moment.").show(activity.getFragmentManager(), "Alert");
        }
        return null;
    }

}
