package g1436218.com.spyder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.UserMap;


public class AttendeeAdapter extends ArrayAdapter<Attendee> {

    private Context context;
    private ArrayList<Attendee> items;

    public AttendeeAdapter(Context context, int resourceId) {
        super(context, resourceId);
        this.context = context;
        updateItem();
    }

    private void updateItem() {
        Iterator iterator = UserMap.getInstance().keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = UserMap.getInstance().get(key);
            add(new Attendee(key, value));
        }
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_attendees, null);
        }

        TextView username = (TextView) v.findViewById(R.id.textview_listview_attendees_username);

        Attendee item = getItem(position);
        username.setText(item.getUsername());

        return v;
    }

}
