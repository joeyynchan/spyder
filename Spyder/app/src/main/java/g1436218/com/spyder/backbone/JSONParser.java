package g1436218.com.spyder.backbone;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    private final String TAG = "JSONParser";

    private static JSONParser instance;

    private InputStream is = null;
    private static JSONObject jObj = null;
    private String json = "";


    // Empty constructor
    public JSONParser() {}

    public static JSONParser getInstance() {
        if (instance == null) {
            instance = new JSONParser();
        }
        return instance;
    }

    public static JSONObject toJSONObject(String json) {
        // Parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
        }

        return jObj;
    }

    public String getStringFromUrl(String url, List<NameValuePair> params) {

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG+"UnsupportedEncodingException", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e(TAG+"ClientProtocalException", e.getMessage());
        } catch (IOException e) {
            Log.e(TAG+"IOException", e.getMessage());
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
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
        return json;
    }

}
