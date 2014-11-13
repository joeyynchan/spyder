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
import g1436218.com.spyder.object.UserMap;

public class FetchAttendeeList extends BaseAsyncTask {

    private final String TAG = "FetchAttendeeList";
    private final String URL = "http://146.169.46.38:8080/MongoDBWebapp/eventUsers?event_id=545ad315e4b0f46082caaef3";

    private UserMap userMap;

    public FetchAttendeeList(Activity activity) {
        super(activity);
        userMap = UserMap.getInstance();
    }

    @Override
    protected Void doInBackground(Void... params) {
        result = getStringFromUrl(URL);

        /* Dummy response since the API is not ready; */
        result = "{\"user_mappings\":[{\"mac_address\":\"38:2D:D1:1B:09:2A\",\"user_name\":\"Joey\"},{\"mac_address\":\"F0:E7:7E:52:57:3E\",\"user_name\":\"Joey2\"},{\"mac_address\":\"98:6D:2E:BD:E9:87\",\"user_name\":\"Cherie\"}]}";

        resultJObj = toJSONObject(result);
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        userMap.updateList(resultJObj);
        Log.d(TAG, userMap.toString());
    }
}
