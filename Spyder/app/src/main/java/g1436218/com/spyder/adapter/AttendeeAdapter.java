package g1436218.com.spyder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.Attendee;

public class AttendeeAdapter extends ArrayAdapter<Attendee> {

    private Context context;

    public AttendeeAdapter(Context context, int resourceId) {
        super(context, resourceId);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_attendees, null);
        }

        TextView username = (TextView) v.findViewById(R.id.textview_listview_attendees_username);
        ImageView image = (ImageView) v.findViewById(R.id.imageview_listview_attendee_icon);

        Attendee item = getItem(position);
        username.setText(item.getName());
        new DownloadImageTask(image).execute(item.getPhoto_url());

        return v;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
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
            }
        }
    }
}
