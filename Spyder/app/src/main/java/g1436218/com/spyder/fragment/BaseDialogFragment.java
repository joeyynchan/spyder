package g1436218.com.spyder.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Joey on 09/11/2014.
 */
public class BaseDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
