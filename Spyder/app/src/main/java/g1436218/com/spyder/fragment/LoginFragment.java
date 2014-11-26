package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.CreateAccount;
import g1436218.com.spyder.asyncTask.LinkDevice;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "RegisterFragment";

    Button button_attemptLogin;
    EditText edittext_username;
    EditText edittext_password;
    TextView textview_errmsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        button_attemptLogin = (Button) rootView.findViewById(R.id.button_fraagment_login_attemptLogin);
        button_attemptLogin.setOnClickListener(this);

        edittext_username = (EditText) rootView.findViewById(R.id.edittext_fragment_login_username);
        edittext_password = (EditText) rootView.findViewById(R.id.edittext_fragment_login_password);
        textview_errmsg = (TextView) rootView.findViewById(R.id.textview_fragment_login_errmsg);

        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String username = sharedPref.getString(context.getString(R.string.username), "");
        String password = sharedPref.getString(context.getString(R.string.password), "");
        edittext_username.setText(username);
        edittext_password.setText(password);

        if(!username.equals("")){
            new LinkDevice(getActivity(), username, password).execute();
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        attemptLogin();
    }

    @Override
    public void onStart(){
        Log.d(TAG, "onResume()");
        edittext_username.setText("");
        edittext_password.setText("");
        textview_errmsg.setText("");
        super.onStart();
    }

    private void attemptLogin(){
        //send login data to api
        Log.d(TAG, "attemptLogin");
        String username = edittext_username.getText().toString();
        String password = edittext_password.getText().toString();
        new LinkDevice(getActivity(), username, password).execute();
    }


}
