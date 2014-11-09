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

public class FetchAttendeeList extends AsyncTask<Void, Void, Void> {

    private final String TAG = "FecthAttendeeList";
    private final String URL = "http://146.169.46.38:8080/MongoDBWebapp/eventUsers?event_id=545ad315e4b0f46082caaef3";

    private Activity activity;
    private String json;
    private InputStream is;

    public FetchAttendeeList(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG + "UnsupportedEncodingException", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e(TAG+"ClientProtocalException", e.getMessage());
        } catch (IOException e) {
            Log.e(TAG+"IOException", e.toString());
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        TextView textView3 = (TextView) activity.findViewById(R.id.textView3);
        textView3.setText(json);
    }
}
