package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

public class SendMessage extends AsyncTask<Void, Void, Void> {

    private final String apiKey = "AIzaSyBz679jaiOiPAcGVXX95QXhU-l0e3sxtAE";

    String title;
    String message;
    JSONObject data;
    ArrayList<String> registration_ids;

    public SendMessage(String title, String message, String sender, String gcm_id) {
        this.data = new JSONObject();
        try {
            this.data.put("action", "SHOW_MESSAGE");
            this.data.put("title", title);
            this.data.put("message", message);
            this.data.put("sender", sender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.registration_ids = new ArrayList<String>();
        this.registration_ids.add(gcm_id);

    }

    @Override
    protected Void doInBackground(Void... _params) {

        try {

            HttpPost httpPost = new HttpPost("https://android.googleapis.com/gcm/send");
            JSONObject params = new JSONObject();
            params.put("data", data);
            params.put("registration_ids", new JSONArray(registration_ids));
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "key="+apiKey);
            httpPost.setEntity(new StringEntity(params.toString()));

            HttpResponse response = new DefaultHttpClient().execute(httpPost);
            Log.d("SendMessage", response.getStatusLine().toString());

            InputStream inputStream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            Log.d("SendMessage", sb.toString());
        } catch (Exception e) {
            Log.d("SendMessage", e.getMessage());
        }
        return null;
    }

}
