package g1436218.com.spyder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.object.Action;

/**
 * Created by Joey on 02/12/2014.
 */
public class LoginActivityReceiver extends BroadcastReceiver {

    LoginActivity activity;

    public LoginActivityReceiver(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Action.GET_GCM.equals(action)) {
            String regid = intent.getExtras().getString("GCMID");
            activity.addGCMToClipBoard(regid);
        }
    }
}
