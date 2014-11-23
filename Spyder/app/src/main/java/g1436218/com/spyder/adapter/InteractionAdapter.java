package g1436218.com.spyder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;

public class InteractionAdapter extends ArrayAdapter<Interaction> {

    private Context context;
    private ArrayList<Interaction> list;

    public InteractionAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.list = new ArrayList<Interaction>();
    }

    /* Update the adapter from the entries in the local cache list */
    public void addAllToAdapter(HashSet<Interaction> interactions) {
        clear();
        Iterator<Interaction> iterator = interactions.iterator();
        while (iterator.hasNext()) {
            Interaction interaction = iterator.next();
            add(interaction);
        }
        list.clear();
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_interaction, null);
        }

        TextView username = (TextView) v.findViewById(R.id.textview_listview_interaction_username);
        TextView strength = (TextView) v.findViewById(R.id.textview_listview_interaction_strength);

        Interaction item = getItem(position);
        username.setText(item.getUsername());
        strength.setText(new Integer(item.getStrength()).toString() + "dBm");

        return v;
    }


}
