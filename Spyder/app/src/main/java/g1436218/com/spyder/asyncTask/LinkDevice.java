package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;

import static android.bluetooth.BluetoothAdapter.getDefaultAdapter;

/**
 * Created by Cherie on 11/9/2014.
 */
public class LinkDevice extends BaseAsyncTask{

    private boolean linked;
    private static String TAG = "LinkDevice";
    public LinkDevice(Activity activity) { super(activity); }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground");
        EditText editText_login_username = (EditText) activity.findViewById(R.id.login_edittext_username);
        String username = editText_login_username.getText().toString();
        EditText editText_login_password = (EditText) activity.findViewById(R.id.login_edittext_password);
        String password = editText_login_password.getText().toString();

        addToParams("username", username);
        addToParams("password", password);
        addToParams("mac", getDefaultAdapter().getAddress());

        //getJSONFromUrl(url)
        /* todo: decode the data received from api call
         * 3 cases
         * 1) logged in successfully (either already in system/create new link by db
         * 2) prompt user to unlink previous device and login with this device
         * 3) account not created, prompt user to register
         */
        return null;
    }

    //Start MainActivity if login succeed
    @Override
    public void onPostExecute(Void v) {
        //if (linked) {
        Log.d(TAG, "onPostExecute");
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
//        } else {
//            //display error message
//            TextView login_text_errmsg = (TextView) activity.findViewById(R.id.login_text_errmsg);
//            login_text_errmsg.setText("Login failed\n");
//
//        }

    }
}


