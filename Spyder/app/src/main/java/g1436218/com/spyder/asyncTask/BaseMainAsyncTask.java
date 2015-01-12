package g1436218.com.spyder.asyncTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

public abstract class BaseMainAsyncTask extends BaseAsyncTask {

    protected MainActivity activity;
    protected ProgressDialog dialog;
    protected boolean offline = false;

    protected abstract Void doInBackgroundOnline(Void... params);

    public BaseMainAsyncTask(MainActivity activity) {
        super();
        this.activity = activity;
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
        return doInBackgroundOnline();
    }

    protected  Void doInBackgroundOffline(Void... params) {
        offline = true;
        return null;
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
