package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.UserMap;

public class FetchAttendeeList extends BaseMainAsyncTask {

    private final String TAG = "FetchAttendeeList";
    private final String URL = GlobalConfiguration.DEFAULT_URL + "eventUsers?event_id=54751551e4b08c8af4a64db3";

    private UserMap userMap;

    public FetchAttendeeList(MainActivity activity) {
        super(activity);
        this.userMap = UserMap.getInstance();
    }

    @Override
    protected Void doInBackground(Void... params) {
        result = getStringFromUrl(URL, Responses.POST);

        /* Dummy response since the API is not ready; */
        result = "{\"user_mappings\":[{\"mac_address\":\"38:2D:D1:1B:09:2A\",\"user_name\":\"Joey\"},{\"mac_address\":\"F0:E7:7E:52:57:3E\",\"user_name\":\"Joey2\"},{\"mac_address\":\"54:27:1E:AB:B7:5A\",\"user_name\":\"Cherie-PC\"},{\"mac_address\":\"98:6D:2E:BD:E9:87\",\"user_name\":\"Cherie\"},{\"mac_address\":\"48:74:6E:75:64:75\",\"user_name\":\"Joey3\"}]}";
        resultJObj = toJSONObject(result);
        //resultJObj = getJSONFromUrl(result, Responses.GET);
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        userMap.updateList(resultJObj);
    }
}
