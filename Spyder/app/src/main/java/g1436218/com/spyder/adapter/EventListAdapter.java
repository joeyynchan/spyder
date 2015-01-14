package g1436218.com.spyder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.Event;


public class EventListAdapter extends BaseAdapter {

    private Context context;
    private int resource;

    private Map<String, EventAdapter> sections = new HashMap<String, EventAdapter>();
    private static int TYPE_SECTION_HEADER = 0;
    private static int TYPE_SECTION_CONTENT = 1;

    public EventListAdapter(Context context, int resource) {
        super();
        this.context = context;
        this.resource = resource;
    }

    public EventAdapter getSection(String caption) {
        return sections.get(caption);
    }

    public void addSection(String caption) {
        EventAdapter adapter = new EventAdapter(context, resource);
        sections.put(caption, adapter);
    }

    public void addItem(String caption, Event item){
        if (!sections.containsKey(caption)) {
            addSection(caption);
        }
        EventAdapter adapter = sections.get(caption);
        if (adapter != null) {
            adapter.add(item);
            adapter.notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    public void clear() {
        sections.clear();
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            EventAdapter adapter = sections.get(caption);
            if (position == 0) {
                return caption;
            }

            int size = adapter.getCount() + 1;

            if (position < size) {
                return sections.get(caption).getItem(position - 1);
            }
            position -= size;
        }
        return null;
    }

    @Override
    public int getCount() {
        int total = 0;
        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            EventAdapter adapter = sections.get(caption);
            total += adapter.getCount() + 1;
        }
        return total;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int getItemViewType(int position) {
        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            EventAdapter adapter = sections.get(caption);
            if (position == 0) {
                return TYPE_SECTION_HEADER;
            }

            int size = adapter.getCount() + 1;

            if (position < size) {
                return TYPE_SECTION_CONTENT;
            }
            position -= size;
        }
        return (-1);
    }

    public boolean areAllItemsSelectable() {
        return (false);
    }

    public boolean isEnabled(int position) {
        return (getItemViewType(position) != TYPE_SECTION_HEADER);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            EventAdapter adapter = sections.get(caption);
            if (position == 0) {
                return (getHeaderView(caption, convertView, parent));
            }

            int size = adapter.getCount() + 1;

            if (position < size) {
                return (adapter.getView(position - 1, convertView, parent));
            }
            position -= size;
        }

        return (null);
    }

    public View getHeaderView(String caption, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.header_event, null);
        }

        TextView text = (TextView) v.findViewById(R.id.textview_header_event);
        text.setText(caption);

        return v;
    }

    private class EventAdapter extends ArrayAdapter<Event> {

        private Context context;

        public EventAdapter(Context context, int resource) {
            super(context, resource);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listview_event, null);
            }

            TextView name = (TextView) v.findViewById(R.id.textview_listview_event_name);

            Event item = getItem(position);
            name.setText(item.getName());

            return v;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.getCount(); i++) {
                sb.append(this.getItem(i).getName() + " --> ");
            }
            return sb.toString();
        }

    }

    public void sort() {
        Log.d("EventlistAdapter", "sort() is called!");
        for (String key: sections.keySet()) {
            EventAdapter eventAdapter = sections.get(key);
            eventAdapter.sort(new Comparator<Event>() {
                @Override
                public int compare(Event lhs, Event rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
            Log.d("EventAdapter", eventAdapter.toString());
            eventAdapter.notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }


}
