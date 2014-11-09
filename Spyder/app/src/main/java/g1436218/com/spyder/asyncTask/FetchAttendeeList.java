package g1436218.com.spyder.asyncTask;

import android.app.Activity;
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

public class FetchAttendeeList extends BaseAsyncTask {

    private final String TAG = "FetchAttendeeList";
    private final String URL = "http://146.169.46.38:8080/MongoDBWebapp/eventUsers?event_id=545ad315e4b0f46082caaef3";

    public FetchAttendeeList(Activity activity) {
        super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        result = getStringFromUrl(URL);
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        TextView textView3 = (TextView) activity.findViewById(R.id.textView3);
        textView3.setText(result);
    }
}
