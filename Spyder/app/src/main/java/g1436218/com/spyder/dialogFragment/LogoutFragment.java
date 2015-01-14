package g1436218.com.spyder.dialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.BaseActivity;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.UnlinkDevice;

public class LogoutFragment extends DialogFragment {

    private final String TAG = "LogoutFragment";
    protected String username;
    protected String password;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        username = ((BaseActivity) getActivity()).getSharedPrefString("Username");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage(username + ", are you sure?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "Yes, please log me out");

                        username = ((BaseActivity) getActivity()).getSharedPrefString("Username");
                        password = ((BaseActivity) getActivity()).getSharedPrefString("Password");
                        ((BaseActivity) getActivity()).clearSharedPref();
                        new UnlinkDevice((MainActivity) getActivity(), username, password).execute();
                        /* Start Login Activity */
                        Activity activity = getActivity();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "No, I would like to continue using this app");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
