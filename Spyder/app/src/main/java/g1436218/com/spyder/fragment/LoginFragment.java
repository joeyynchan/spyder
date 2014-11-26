package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.CreateAccount;
import g1436218.com.spyder.asyncTask.LinkDevice;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "RegisterFragment";

    Button button_attemptLogin;
    EditText edittext_username;
    EditText edittext_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        button_attemptLogin = (Button) rootView.findViewById(R.id.button_fraagment_login_attemptLogin);
        button_attemptLogin.setOnClickListener(this);

        edittext_username = (EditText) rootView.findViewById(R.id.edittext_fragment_login_username);
        edittext_password = (EditText) rootView.findViewById(R.id.edittext_fragment_login_password);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        attemptLogin();
    }

    private void attemptLogin(){
        //send login data to api
        Log.d(TAG, "attemptLogin");
        String username = edittext_username.getText().toString();
        String password = edittext_password.getText().toString();
        new LinkDevice((LoginActivity) getActivity(), username, password).execute();
    }


}
