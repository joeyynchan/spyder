package g1436218.com.spyder.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.fragment.MessageFragment;
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

        Attendee item = getItem(position);
        username.setText(item.getName());


        return v;
    }

}
