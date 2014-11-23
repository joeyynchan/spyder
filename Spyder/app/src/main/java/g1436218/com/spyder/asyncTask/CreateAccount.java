package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.fragment.RegisterFragment;

/**
 * Created by Cherie on 11/9/2014.
 */
public class CreateAccount extends BaseAsyncTask {

    private RegisterFragment fragment;
    private final String TAG = "RegisterFragment";
    private final String URL = GlobalConfiguration.DEFAULT_URL + "register";

    public CreateAccount(RegisterFragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
    }

    @Override
    protected Void doInBackground(Void... params) {

        EditText register_edittext_name = (EditText) fragment.getView().findViewById(R.id.register_edittext_name);
        String username = register_edittext_name.getText().toString();
        Log.d(TAG, username);
        EditText register_edittext_password1 = (EditText) fragment.getView().findViewById(R.id.register_edittext_password1);
        String password = register_edittext_password1.getText().toString();
        Log.d(TAG, password);

        addToParams("user_name", username);
        addToParams("password", password);
        JSONObject jsonObject = getJSONFromUrl(URL);
        Log.d(TAG, jsonObject.toString());

        return null;
    }

    @Override
    public void onPostExecute(Void v){
        Log.d(TAG, "onPostExecute");
        //Registration was successful
        if(statusCode == 200) {
            TextView login_text_errmsg = (TextView) activity.findViewById(R.id.login_text_errmsg);
            login_text_errmsg.setText("Account has been successfully created\n");
            fragment.dismiss();
        }
    }
}
