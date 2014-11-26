package g1436218.com.spyder.asyncTask;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.fragment.RegisterFragment;

/**
 * Created by Cherie on 11/9/2014.
 */
public class CreateAccount extends BaseLoginAsyncTask {

    private RegisterFragment fragment;
    private final String TAG = "RegisterFragment";
    private final String URL = GlobalConfiguration.DEFAULT_URL + "register";

    public CreateAccount(LoginActivity activity) {
        super(activity);
        this.fragment = fragment;
    }

    @Override
    protected Void doInBackground(Void... params) {

        EditText register_edittext_name = (EditText) activity.findViewById(R.id.edittext_fragment_register_name);
        String username = register_edittext_name.getText().toString();
        Log.d(TAG, username);
        EditText register_edittext_password1 = (EditText) activity.findViewById(R.id.edittext_fragment_register_password1);
        String password = register_edittext_password1.getText().toString();
        Log.d(TAG, password);

        addToParams("user_name", username);
        addToParams("password", password);
        JSONObject jsonObject = getJSONFromUrl(URL, Responses.POST);

        return null;
    }

    @Override
    public void onPostExecute(Void v){
        Log.d(TAG, "onPostExecute");
        Log.d(TAG, statusCode + "");
        if(statusCode == 201) {
            //Registration was successful
            TextView login_text_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_register_errmsg);
            login_text_errmsg.setText("Account has been successfully created\n");
            activity.displayLoginFragment();
        }else{
            TextView login_text_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_register_errmsg);
            login_text_errmsg.setText("Registration failed\n");

        }
    }
}
