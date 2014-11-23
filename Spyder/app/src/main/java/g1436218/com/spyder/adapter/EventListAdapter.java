package g1436218.com.spyder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Event;


public class EventListAdapter extends ArrayAdapter<Event> {

    private Context context;

    public EventListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        startDemo();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_event, null);
        }

        TextView name = (TextView) v.findViewById(R.id.textview_listview_event_name);
        TextView location = (TextView) v.findViewById(R.id.textview_listview_event_location);

        Event item = getItem(position);
        name.setText(item.getName());
        location.setText(item.getLocation());

        return v;
    }

    private void startDemo() {
        add(new Event("Event 1", null, null, null, null, "308", null));
        add(new Event("Event 2", null, null, null, null, "311", null));
        add(new Event("Event 3", null, null, null, null, "217B", null));
    }


}
