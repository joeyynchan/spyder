package g1436218.com.spyder.fragment;

import android.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.EventListAdapter;
import g1436218.com.spyder.asyncTask.FetchEvents;
import g1436218.com.spyder.intentfilter.EventListFragmentIntentFilter;
import g1436218.com.spyder.object.Event;
import g1436218.com.spyder.receiver.EventListFragmentReceiver;

public class EventListFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

    private final String TITLE = "Event List";

    private ListView listview_eventlist;
    private SearchView searchview_eventlist;
    private EventListAdapter adapter;

    public EventListFragment(MainActivity activity) {
        super(activity, R.layout.fragment_eventlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        new FetchEvents(this, "").execute();
    }

    @Override
    protected void registerReceiver() {
        receiver = new EventListFragmentReceiver(this);
        activity.registerReceiver(receiver, new EventListFragmentIntentFilter());
    }

    @Override
    protected void initializeView() {
        getActivity().setTitle(TITLE);
        this.adapter = new EventListAdapter(activity, R.layout.listview_interaction);
        listview_eventlist = (ListView) activity.findViewById(R.id.listview_eventlist);
        listview_eventlist.setAdapter(adapter);
        listview_eventlist.setOnItemClickListener(this);

        searchview_eventlist = (SearchView) activity.findViewById(R.id.searchview_eventlist);
        searchview_eventlist.setOnQueryTextListener(this);
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

    public void addItem(String caption, Event event) {
        adapter.addItem(caption, event);
    }

    public void clearAdapter() {
        adapter.clear();
    }

    public void searchEvent() {
        String keyword = searchview_eventlist.getQuery().toString();
        searchEvent(keyword);
    }

    private void searchEvent(String keyword) {
        new FetchEvents(this, keyword).execute();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchEvent(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String keyword = searchview_eventlist.getQuery().toString();
        searchEvent(keyword);
        return false;
    }
}
