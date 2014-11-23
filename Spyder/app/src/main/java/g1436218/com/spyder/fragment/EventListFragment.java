package g1436218.com.spyder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.adapter.EventListAdapter;
import g1436218.com.spyder.adapter.InteractionAdapter;

public class EventListFragment extends BaseFragment {

    private final String TITLE = "Event List";

    private ListView listview_eventlist;
    private EventListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new EventListAdapter(getActivity(), R.layout.listview_interaction);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eventlist, container, false);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(TITLE);
        listview_eventlist = (ListView) getActivity().findViewById(R.id.listview_eventlist);
        listview_eventlist.setAdapter(adapter);
        super.onResume();
    }


}
