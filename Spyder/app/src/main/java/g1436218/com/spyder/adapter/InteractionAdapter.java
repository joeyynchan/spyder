package g1436218.com.spyder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DownloadImageTask;
import g1436218.com.spyder.object.Interaction;

public class InteractionAdapter extends ArrayAdapter<Interaction> {

    private static final String TAG = "InteractionAdapter";
    private Context context;

    public InteractionAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public void addAllToAdapter(ArrayList<Interaction> interactions) {
        clear();
        addAll(interactions);
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_interaction, null);
        }

        TextView name = (TextView) v.findViewById(R.id.textview_listview_interaction_username);
        TextView strength = (TextView) v.findViewById(R.id.textview_listview_interaction_strength);
        ImageView image = (ImageView) v.findViewById(R.id.imageview_listview_interaction_icon);

        Interaction item = getItem(position);
        name.setText(item.getName());
        if(item.getPhoto_url().equals("")){
            Log.d(TAG, "Display default picture for " + item.getAttendee().toString());
            image.setImageResource(R.drawable.main_activity_user_normal);
        }else {
            if (item.getPhoto() == null) {
                new DownloadImageTask(image, item.getAttendee()).execute(item.getPhoto_url());
            } else {
                image.setImageBitmap(item.getPhoto());
            }
        }
        strength.setText(new Integer(item.getStrength()).toString() + "dBm");


        return v;
    }


}
