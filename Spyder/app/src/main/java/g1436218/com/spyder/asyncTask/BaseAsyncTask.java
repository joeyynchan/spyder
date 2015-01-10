package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


public abstract class BaseAsyncTask extends AsyncTask<Void, Void, Void> {

    public enum Requests {
        DELETE, GET, POST, PUT;
    }

    private final String TAG = "BaseAsyncTask";

    protected InputStream inputStream;
    protected String result;
    protected JSONObject resultJObj;
    protected JSONObject params;
    protected int statusCode = 0;

    protected BaseAsyncTask(){
        this.params = new JSONObject();
        this.result = "Result cannot be fetched";
    }

    protected String getStringFromUrl(String url, Requests request) {
        try {

            HttpRequestBase httpRequest = new HttpRequestBase() {
                @Override
                public String getMethod() {
                    return null;
                }
            };

            switch(request){
                case DELETE:
                    httpRequest = new HttpDelete(url);
                    break;

                case GET:
                    httpRequest = new HttpGet(url);
                    break;

                case POST:
                    httpRequest = new HttpPost(url);
                    ((HttpPost)httpRequest).setEntity(new StringEntity(params.toString()));
                    break;

                case PUT:
                    httpRequest = new HttpPut(url);
                    ((HttpPut) httpRequest).setEntity(new StringEntity(params.toString()));
                    break;

            }
            httpRequest.setHeader("Content-type", "application/json");
            HttpResponse httpResponse =  new DefaultHttpClient().execute(httpRequest);
            if(httpResponse.getEntity() != null) {
                inputStream = httpResponse.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                result = sb.toString();
            }else{
                result = "";
            }

            statusCode = httpResponse.getStatusLine().getStatusCode();


        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return result;
    }

    protected JSONObject getJSONFromUrl(String url, Requests response) {
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

    protected JSONArray toJSONArray(String result) {
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jArray;
    }

    protected void addToParams(String name, Object value){
        try {
            params.put(name, value);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to put new pair into JSON");
        }
    }

    //new URL(user.getPhotoURL()
    protected Bitmap getImageFromURL(URL url){
        Bitmap bitmap = null;
        HttpGet httpRequest = null;

        try {
            httpRequest = new HttpGet(url.toURI());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            response = (HttpResponse) httpclient.execute(httpRequest);

            HttpEntity entity = response.getEntity();
            BufferedHttpEntity b_entity = null;
            b_entity = new BufferedHttpEntity(entity);

            InputStream input = null;
            input = b_entity.getContent();

            bitmap = BitmapFactory.decodeStream(input);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
    protected String addUrlParams(ArrayList<NameValuePair> list){
        NameValuePair elem = list.remove(0);
        String result = "?" + elem.getName() + "=" + elem.getValue();

        for(NameValuePair item : list){
            result = "&" + item.getName() + "=" + item.getValue();
        }
        return result;
    }

}
