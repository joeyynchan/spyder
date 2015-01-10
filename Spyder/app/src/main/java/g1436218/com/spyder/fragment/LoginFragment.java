package g1436218.com.spyder.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.asyncTask.LinkDevice;

public class LoginFragment extends BaseLoginFragment implements View.OnClickListener {

    private final String TAG = "RegisterFragment";

    Button button_attemptLogin;
    EditText edittext_username;
    EditText edittext_password;
    TextView textview_errmsg;

    public LoginFragment(LoginActivity activity) {
        super(activity, R.layout.fragment_login);
    }

    @Override
    protected void initializeView() {
        Log.d(TAG, "onResume - initialiseView");
        button_attemptLogin = (Button) activity.findViewById(R.id.button_fragment_login_attemptLogin);
        button_attemptLogin.setOnClickListener(this);

        edittext_username = (EditText) activity.findViewById(R.id.edittext_fragment_login_username);
        edittext_password = (EditText) activity.findViewById(R.id.edittext_fragment_login_password);
        textview_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_login_errmsg);

        edittext_username.setText("");
        edittext_password.setText("");
        textview_errmsg.setText("");
    }

    private void attemptLogin(){
        //send login data to api
        Log.d(TAG, "attemptLogin");
        String username = edittext_username.getText().toString();
        String password = edittext_password.getText().toString();
        new LinkDevice((LoginActivity) getActivity(), username, password).execute();
    }

    @Override
    public void onClick(View v) {
        attemptLogin();
    }

}
