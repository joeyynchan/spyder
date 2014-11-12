package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;

/**
 * Created by Cherie on 11/9/2014.
 */
public class LinkDevice extends BaseAsyncTask{

    public LinkDevice (Activity activity) { super(activity); }

    @Override
    protected Void doInBackground(Void... params) {
        EditText editText_login_username = (EditText) activity.findViewById(R.id.login_username);
        String username = editText_login_username.getText().toString();
        EditText editText_login_password = (EditText) activity.findViewById(R.id.login_password);
        String password = editText_login_password.getText().toString();

        addToParams("username", username);
        addToParams("password", password);
        //addToParams("mac", macAddress);

        //getJSONFromUrl(url)
        /* todo: decode the data received from api call
         * 3 cases
         * 1) logged in successfully (either already in system/create new link by db
         * 2) prompt user to unlink previous device and login with this device
         * 3) account not created, prompt user to register
         */
        return null;
    }

    @Override
    public void onPostExecute(Void v){

    }
}


