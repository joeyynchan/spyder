package g1436218.com.spyder.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.BaseActivity;
import g1436218.com.spyder.asyncTask.CreateAccount;

public class RegisterFragment extends BaseDialogFragment implements View.OnClickListener {

    private final String TAG = "RegisterFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        Button register_button_register = (Button) rootView.findViewById(R.id.register_button_register);
        register_button_register.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        new CreateAccount(this).execute();
    }
}
