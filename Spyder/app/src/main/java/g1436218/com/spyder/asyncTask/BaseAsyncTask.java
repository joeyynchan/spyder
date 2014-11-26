package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
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
import java.util.Objects;


public abstract class BaseAsyncTask extends AsyncTask<Void, Void, Void> {

    public enum Responses{
        DELETE,
        GET,
        POST,
        PUT;
    }

    private final String TAG = "BaseAsyncTask";

    protected Activity activity;
    protected InputStream inputStream;
    protected String result;
    protected JSONObject resultJObj;
    protected JSONObject params;
    protected int statusCode = 0;

    protected BaseAsyncTask(){
        this.params = new JSONObject();
        this.result = "Result cannot be fetched";
    }

    protected BaseAsyncTask(Activity activity) {
        this.activity = activity;
        this.params = new JSONObject();
        this.result = "Result cannot be fetched";
    }

    protected String getStringFromUrl(String url, Responses response) {

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpRequestBase httpRequest = new HttpRequestBase() {
                @Override
                public String getMethod() {
                    return null;
                }
            };

            switch(response){
                case DELETE:
                    Log.d(TAG, "HttpDelete");
                    httpRequest = new HttpDelete(url);
                    break;

                case GET:
                    Log.d(TAG, "HttpGet");
                    httpRequest = new HttpGet(url);
                    break;

                case POST:
                {
                    Log.d(TAG, "HttpPost");
                    httpRequest = new HttpPost(url);
                    ((HttpPost)httpRequest).setEntity(new StringEntity(params.toString()));
                }
                    break;

                case PUT: {
                    Log.d(TAG, "HttpPut");
                    httpRequest = new HttpPut(url);
                    ((HttpPut) httpRequest).setEntity(new StringEntity(params.toString()));
                }
                    break;

            }

            httpRequest.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
            statusCode = httpResponse.getStatusLine().getStatusCode();
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

    protected JSONObject getJSONFromUrl(String url, Responses response) {
        String result = getStringFromUrl(url, response);
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

    protected void addToParams(String name, Object value){
        //Log.d(TAG, name + " " + value);
        try {
            params.put(name, value);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to put new pair into JSON");
        }
    }
}
