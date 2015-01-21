package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.Attendee;

/**
 * Created by Cherie on 1/15/2015.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Attendee attendee = null;
    private String TAG = "DownloadImageTask";

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    public DownloadImageTask(ImageView bmImage, Attendee attendee){
        this(bmImage);
        this.attendee = attendee;
    }

    protected Bitmap doInBackground(String... urls) {
        Log.d(TAG, "Downloading picture for " + attendee.toString());
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            return null;
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {

        if (result == null) {
            bmImage.setImageResource(R.drawable.ic_launcher);
        } else {
            bmImage.setImageBitmap(result);
            Log.d(TAG, "setting photo");
            attendee.setPhoto(result);
        }

    }
}