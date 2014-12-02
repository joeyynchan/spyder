package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.LinkDevice;
import g1436218.com.spyder.fragment.LoginFragment;
import g1436218.com.spyder.fragment.RegisterFragment;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.receiver.LoginActivityReceiver;

public class LoginActivity extends BaseActivity {

    private final String TAG = "LoginActivity";

    private Button button_signUp;
    private Button button_login;

    private LoginActivityReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState, R.layout.activity_login);
        getActionBar().hide();

        Context context = this;
        SharedPreferences sharedPref = getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(context.getString(R.string.username), "");
        String password = sharedPref.getString(context.getString(R.string.password), "");




        Log.d(TAG, username + ":" + password);
        if(!username.equals("")){
            new LinkDevice(this, username, password).execute();
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_container, new LoginFragment(this), "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        receiver = new LoginActivityReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.GET_GCM);
        registerReceiver(receiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_activity_login_signUp:   displayRegisterFragment(); break;
            case R.id.button_activity_login_login: displayLoginFragment(); break;
            default:    break;
        }
    }

    @Override
    public void initializeView() {
        /* Sign up Button */
        button_signUp = (Button) findViewById(R.id.button_activity_login_signUp);
        button_signUp.setOnClickListener(this);

        /* Log in Button */
        button_login = (Button) findViewById(R.id.button_activity_login_login);
        button_login.setOnClickListener(this);
    }

    public void displayRegisterFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof RegisterFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.login_container, new RegisterFragment(this), "CURRENT_FRAGMENT");
            fragmentTransaction.commit();
        }
        button_signUp.setBackground(getResources().getDrawable(R.drawable.login_activity_button_focused));
        button_login.setBackground(getResources().getDrawable(R.drawable.login_activity_button_normal));
    }

    public void displayLoginFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof LoginFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.login_container, new LoginFragment(this), "CURRENT_FRAGMENT");
            fragmentTransaction.commit();
        }
        button_signUp.setBackground(getResources().getDrawable(R.drawable.login_activity_button_normal));
        button_login.setBackground(getResources().getDrawable(R.drawable.login_activity_button_focused));
    }

    public void addGCMToClipBoard(String regid) {
        ClipData clip = ClipData.newPlainText("gcm", regid);
        ClipboardManager clipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipBoard.setPrimaryClip(clip);
    }

}
