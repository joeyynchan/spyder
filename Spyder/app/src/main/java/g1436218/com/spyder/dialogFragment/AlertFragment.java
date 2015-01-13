package g1436218.com.spyder.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlertFragment extends DialogFragment {

    private final DialogInterface.OnClickListener listener;
    private String title;
    private String message;

    public AlertFragment(String title, String message) {
        this(title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    public AlertFragment(String title, String message, DialogInterface.OnClickListener listener) {
        this.title = title;
        this.message = message;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("Dismiss", listener)
                .create();
    }
}