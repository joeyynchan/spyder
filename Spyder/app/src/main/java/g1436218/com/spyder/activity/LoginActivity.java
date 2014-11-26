package g1436218.com.spyder.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.LinkDevice;
import g1436218.com.spyder.fragment.RegisterFragment;

public class LoginActivity extends BaseActivity {

    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.customOnCreate(savedInstanceState, R.layout.activity_login);
        getActionBar().hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_sign_up:   displayRegisterFragment(); break;
            case R.id.login_button_login: attemptLogin(); break;
            default:    break;
        }
    }

    @Override
    public void initializeView() {
        /* Sign up Button */
        Button login_button_sign_up = (Button) findViewById(R.id.login_button_sign_up);
        login_button_sign_up.setOnClickListener(this);

        /* Log in Button */
        Button login_button_login = (Button) findViewById(R.id.login_button_login);
        login_button_login.setOnClickListener(this);
    }

    @Override
    public void onRestart() {
        Log.d(TAG, "onRestart()");
        EditText editText_login_username = (EditText) findViewById(R.id.login_edittext_username);
        EditText editText_login_password = (EditText) findViewById(R.id.login_edittext_password);
        TextView textview_login_errmsg = (TextView) findViewById(R.id.login_textview_errmsg);
        editText_login_username.setText("");
        editText_login_password.setText("");
        textview_login_errmsg.setText("");

        super.onRestart();
    }

    private void displayRegisterFragment() {
        Log.d(TAG, "displayRegisterFragment()");
        FragmentManager fragmentManager = getFragmentManager();
        new RegisterFragment().show(fragmentManager, "Sign Up");
    }

    private void attemptLogin(){
        //send login data to api
        Log.d(TAG, "attemptLogin");
        new LinkDevice(this).execute();
    }


}
