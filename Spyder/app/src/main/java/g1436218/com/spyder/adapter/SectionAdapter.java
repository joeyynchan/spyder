package g1436218.com.spyder.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import g1436218.com.spyder.R;

abstract public class SectionAdapter<T> extends BaseAdapter {

    String TAG = getClass().getSimpleName();

    private Map<String, ArrayAdapter<T>> sections = new HashMap<String, ArrayAdapter<T>>();
    private static int TYPE_SECTION_HEADER = 0;
    private static int TYPE_SECTION_CONTENT = 1;

    private Context context;
    private int contentResource;
    private int headerResource;

    public SectionAdapter(Context context, int contentResource) {
        super();
        this.context = context;
        this.contentResource = contentResource;
        this.headerResource = headerResource;
        sections.clear();
    }

    public ArrayAdapter<T> getSection(String caption) {
        return sections.get(caption);
    }

    public void addSection(String caption) {
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(context, contentResource);
        sections.put(caption, adapter);
    }

    public void addItem(String caption, T item){
        ArrayAdapter<T> adapter = sections.get(caption);
        if (adapter != null) {
            adapter.add(item);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        sections.clear();
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            ArrayAdapter<T> adapter = sections.get(caption);
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

    public int getCount() {
        int total = 0;
        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            ArrayAdapter<T> adapter = sections.get(caption);
            total += adapter.getCount() + 1;
        }
        return (total);
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int getItemViewType(int position) {
        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            ArrayAdapter<T> adapter = sections.get(caption);
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

    public View getView(int position, View convertView, ViewGroup parent) {

        Iterator<String> iterator = sections.keySet().iterator();
        while (iterator.hasNext()) {
            String caption = iterator.next();
            ArrayAdapter<T> adapter = sections.get(caption);
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

    protected abstract View getHeaderView(String caption, View convertView, ViewGroup parent); {

    }


    public long getItemId(int position) {
        return (position);
    }

}