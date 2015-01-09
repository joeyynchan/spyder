package g1436218.com.spyder.asyncTask;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.dialogFragment.UnlinkFragment;
import g1436218.com.spyder.object.Action;

import static android.bluetooth.BluetoothAdapter.getDefaultAdapter;

public class LinkDevice extends BaseLoginAsyncTask{

    private static String TAG = "LinkDevice";
    private static String URL = GlobalConfiguration.DEFAULT_URL + "login";

    private String username;
    private String password;
    private String macAddress;
    private String gcmID;

    public LinkDevice(LoginActivity activity, String username, String password) {
        super(activity);
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... params) {

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity);
        gcmID = "";
        try {
            gcmID = gcm.register(GlobalConfiguration.PROJECT_NUMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        password = computeSHAHash("test");

        macAddress = getDefaultAdapter().getAddress();

        addToParams("user_name", username);
        addToParams("password", password);
        addToParams("mac_address", macAddress);
        addToParams("gcm_id", gcmID);

        Log.i("GCM", "GCM ID=" + gcmID);

        Intent intent = new Intent();
        intent.setAction(Action.GET_GCM);
        intent.putExtra("GCMID", gcmID);
        activity.sendBroadcast(intent);

        if (username.equals(SharedPref.OFFLINE_MODE)) {
            statusCode = 999;
        } else {
            JSONObject jsonObject = getJSONFromUrl(URL, Requests.POST);
        }
        Log.i(TAG, "Login Attempt: " + username + ":" + password + " ---------- Result:" + statusCode);
        return null;
    }

    //Start MainActivity if login succeed
    @Override
    public void onPostExecute(Void v) {
        switch(this.statusCode) {
            case (200):{
                setErrMsgToNotFound();
                gotoMainActivity();
                }
                break;

            case (201):{
                setErrMsgToNotFound();
                gotoMainActivity();
                }
                break;

            case (404):
                setErrMsgToNotFound();
                break;

            case (409):
                setErrMsgToNotFound();
                showUnlink();
                break;
            case (410):
                setErrMsgToNotFound();
                break;
            case (411):
                setErrMsgToNotFound();
                break;
            case (412):
                setErrMsgToNotFound();
                break;
            case (999):
                gotoMainActivity();

            default:
                setErrMsgToNotFound();
                break;
        }
    }

    private void gotoMainActivity(){
        /* insert (username, password) into sharedPreference */
        activity.putSharedPrefString(SharedPref.USERNAME, username);
        activity.putSharedPrefString(SharedPref.PASSWORD, password);
        activity.putSharedPrefString(SharedPref.MAC_ADDRESS, macAddress);
        activity.putSharedPrefString(SharedPref.GCM_ID, gcmID);
        activity.putSharedPrefBoolean(SharedPref.OFFLINE_MODE, username.equals(SharedPref.OFFLINE_MODE));

        /* start mainActivity */
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void setErrMsgToNotFound(){
        TextView login_text_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_login_errmsg);
        if(login_text_errmsg != null) {
            switch (this.statusCode) {
                case (200):
                    login_text_errmsg.setText("Login successfully\n");
                    break;
                case (201):
                    login_text_errmsg.setText("Successfully linked device\n");
                    break;
                case (404):
                    login_text_errmsg.setText("Username/Password not match\n");
                    break;
                case (409):
                    login_text_errmsg.setText("User is already linked with other device\n");
                    break;
                case 410:
                    login_text_errmsg.setText("Username cannot be empty");
                    break;
                case 411:
                    login_text_errmsg.setText("Password cannot be empty");
                    break;
                case 412:
                    login_text_errmsg.setText("This device is used by another user.");
                    break;
                default:
                    login_text_errmsg.setText("No response from server, please try again later\n");
                    break;

            }
        }
    }

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }

    private void showUnlink() {
                /* insert (username, password) into sharedPreference */
        activity.putSharedPrefString(SharedPref.USERNAME, username);
        activity.putSharedPrefString(SharedPref.PASSWORD, password);
        FragmentManager fragmentManager = activity.getFragmentManager();
        new UnlinkFragment().show(fragmentManager, "Unlink");
        TextView textview_errmsg = (TextView) activity.findViewById(R.id.textview_fragment_login_errmsg);

        textview_errmsg.setText("");
    }

    private static String convertToHex(byte[] data) throws java.io.IOException
    {


        StringBuffer sb = new StringBuffer();
        String hex=null;

        hex= Base64.encodeToString(data, 0, data.length, 0);

        sb.append(hex);

        return sb.toString();
    }


    public String computeSHAHash(String password)
    {
        String SHAHash = "";

        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("myapp", "Error initializing SHA1 message digest");
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        SHAHash=bytesToHex(data);

        return SHAHash;

    }
    final protected static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex( byte[] bytes )
    {
        char[] hexChars = new char[ bytes.length * 2 ];
        for( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[ j ] & 0xFF;
            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
        }
        return new String( hexChars );
    }
}

