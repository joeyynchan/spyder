package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

import static android.bluetooth.BluetoothAdapter.getDefaultAdapter;

/**
 * Created by Cherie on 11/9/2014.
 */
public class LinkDevice extends BaseAsyncTask{

    private boolean linked = false;
    private static String TAG = "LinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";
    public LinkDevice(Activity activity) { super(activity); }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "doInBackground");
        EditText editText_login_username = (EditText) activity.findViewById(R.id.login_edittext_username);
        String username = editText_login_username.getText().toString();
        EditText editText_login_password = (EditText) activity.findViewById(R.id.login_edittext_password);
        String password = editText_login_password.getText().toString();

        addToParams("user_name", username);
        addToParams("password", password);
        addToParams("mac_address", getDefaultAdapter().getAddress());

        JSONObject jsonObject = getJSONFromUrl(URL);
        Log.d(TAG, this.statusCode + "");

        return null;
    }

    //Start MainActivity if login succeed
    @Override
    public void onPostExecute(Void v) {
        Log.d(TAG, "onPostExecute");
        setStatusCode(404);
        switch(this.statusCode) {
            case (200):{
                gotoMainActivity();
                setErrMsgToNotFound();}
                break;

            case (201):{
                gotoMainActivity();
                setErrMsgToNotFound();}
                break;

            case (404):
                setErrMsgToNotFound();
                break;

            case (409):
                setErrMsgToNotFound();
                break;

            default:
                setErrMsgToNotFound();
                break;
        }
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    private void setErrMsgToNotFound(){
        TextView login_text_errmsg = (TextView) activity.findViewById(R.id.login_text_errmsg);

        switch(this.statusCode) {
            case(200): login_text_errmsg.setText("Login successfully\n");
                break;
            case(201): login_text_errmsg.setText("Successfull linked device\n");
                break;
            case(404): login_text_errmsg.setText("Username/Password not match\n");
                break;
            case(409): login_text_errmsg.setText("User is already linked with other device\n");
                break;
            default:
                break;

        }
    }

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
}


