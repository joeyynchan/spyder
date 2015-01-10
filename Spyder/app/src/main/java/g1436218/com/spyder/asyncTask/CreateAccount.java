package g1436218.com.spyder.asyncTask;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

public class CreateAccount extends BaseLoginAsyncTask {

    private final String TAG = "RegisterFragment";
    private final String URL = GlobalConfiguration.DEFAULT_URL + "register";

    public CreateAccount(LoginActivity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {

        EditText register_edittext_name = (EditText) activity.findViewById(R.id.edittext_fragment_register_name);
        EditText register_edittext_password1 = (EditText) activity.findViewById(R.id.edittext_fragment_register_password1);

        String username = register_edittext_name.getText().toString();
        String password = register_edittext_password1.getText().toString();
        Log.d(TAG, username + " : " + password);

        addToParams("user_name", username);
        addToParams("password", password);
        JSONObject jsonObject = getJSONFromUrl(URL, Requests.POST);

        Log.i(TAG, "Register Attempt: " + username + ":" + password + " ---------- Result: " + statusCode);

        return null;
    }

    @Override
    public void onPostExecute(Void v){
        TextView login_text_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_register_errmsg);
        if (statusCode == 201) {
            //Registration was successful
            login_text_errmsg.setText("Account has been successfully created\n");
            activity.displayLoginFragment();
        } else {
            login_text_errmsg.setText("Registration failed\n");
        }
    }
}
