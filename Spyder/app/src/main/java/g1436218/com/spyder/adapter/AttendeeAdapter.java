package g1436218.com.spyder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DownloadImageTask;
import g1436218.com.spyder.object.Attendee;

public class AttendeeAdapter extends ArrayAdapter<Attendee> {

    private static final String TAG = "AttendeeAdapter";
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
        if(item.getPhoto_url().equals("")){
            Log.d(TAG, "Display default picture for " + item.toString());
            image.setImageResource(R.drawable.main_activity_user_normal);
        }else {
            if (item.getPhoto() == null) {
                new DownloadImageTask(image, item).execute(item.getPhoto_url());
            } else {
                image.setImageBitmap(item.getPhoto());
            }
        }
        return v;
    }


}
