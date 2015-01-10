package g1436218.com.spyder.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.UnlinkDevice;

public class UnlinkFragment extends DialogFragment {

    private final String TAG = "LogoutFragment";
    protected String username;
    protected String password;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("You are currently logged in on another device. Would you like to log out from that device?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "Yes, please unlink");
                        Context context = getActivity();
                        SharedPreferences sharedPref = context.getSharedPreferences(
                                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        username = sharedPref.getString(context.getString(R.string.username), "");
                        password = sharedPref.getString(context.getString(R.string.password), "");
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.remove(context.getString(R.string.username));
                        editor.remove(context.getString(R.string.password));
                        editor.commit();
                        new UnlinkDevice(getActivity(), username, password).execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "No, I would like to remain logged in on that device");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
