package g1436218.com.spyder.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.UnlinkDevice;

/**
 * Created by Cherie on 11/26/2014.
 */
public class LogoutFragment extends BaseDialogFragment {

    private final String TAG = "LogoutFragment";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "Yes, please log me out");
                        new UnlinkDevice((MainActivity)getActivity()).execute();
                        getActivity().finish();
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
