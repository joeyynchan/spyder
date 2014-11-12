package g1436218.com.spyder.activity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayMacAddress;
import g1436218.com.spyder.asyncTask.LinkDevice;
import g1436218.com.spyder.fragment.RegisterFragment;

public class LoginActivity extends BaseActivity {

    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.customOnCreate(savedInstanceState, R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        Button button_sign_up = (Button) findViewById(R.id.login_button_sign_up);
        button_sign_up.setOnClickListener(this);
        Button button_main_activity = (Button) findViewById(R.id.login_button_login);
        button_main_activity.setOnClickListener(this);
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

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
