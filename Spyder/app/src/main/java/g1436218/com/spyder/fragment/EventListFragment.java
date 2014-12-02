package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.adapter.EventListAdapter;
import g1436218.com.spyder.adapter.InteractionAdapter;
import g1436218.com.spyder.object.Event;

public class EventListFragment extends BaseMainFragment implements AdapterView.OnItemClickListener {

    private final String TITLE = "Event List";

    private ListView listview_eventlist;
    private EventListAdapter adapter;

    public EventListFragment(MainActivity activity) {
        super(activity, R.layout.fragment_eventlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        //new FetchEvents(activity).execute();
    }

    @Override
    protected void initializeView() {
        getActivity().setTitle(TITLE);
        this.adapter = new EventListAdapter(activity, R.layout.listview_interaction);
        listview_eventlist = (ListView) getActivity().findViewById(R.id.listview_eventlist);
        listview_eventlist.setAdapter(adapter);
        listview_eventlist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event item = (Event) parent.getItemAtPosition(position);
        EventFragment eventFragment = new EventFragment(activity, item);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
