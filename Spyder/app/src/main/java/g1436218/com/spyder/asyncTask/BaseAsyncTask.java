package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public abstract class BaseAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String TAG = "BaseAsyncTask";

    protected Activity activity;
    protected InputStream inputStream;
    protected String result;
    protected JSONObject resultJObj;
    protected ArrayList<NameValuePair> params;

    protected BaseAsyncTask(Activity activity) {
        this.activity = activity;
        this.params = new ArrayList<NameValuePair>();
        this.result = "Result cannot be fetched";
    }

    protected String getStringFromUrl(String url) {

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    protected JSONObject getJSONFromUrl(String url) {
        String result = getStringFromUrl(url);
        return toJSONObject(result);
    }

    protected JSONObject toJSONObject(String result) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(result);
        } catch (JSONException e) {
        }
        return jObj;
    }

    /* Helper method for adding a BasicNameValuePair to the list of params for httpPost request */
    protected void addToParams(String name, String value){
        params.add(new BasicNameValuePair(name, value));
    }
}
