package g1436218.com.spyder.fragment;

import android.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.EventListAdapter;
import g1436218.com.spyder.asyncTask.FetchEventDetails;
import g1436218.com.spyder.asyncTask.FetchEvents;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.intentfilter.EventListFragmentIntentFilter;
import g1436218.com.spyder.object.Event;
import g1436218.com.spyder.receiver.EventListFragmentReceiver;

public class EventListFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private final String TITLE = "Event List";

    private ListView listview_eventlist;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchview_eventlist;
    private EventListAdapter adapter;

    public EventListFragment(MainActivity activity) {
        super(activity, R.layout.fragment_eventlist);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchEvent();
    }

    @Override
    protected void registerReceiver() {
        receiver = new EventListFragmentReceiver(this);
        activity.registerReceiver(receiver, new EventListFragmentIntentFilter());
    }

    @Override
    protected void initializeView() {

        String event_id = activity.getSharedPrefString(SharedPref.EVENT_ID);
        String event_name = activity.getSharedPrefString(SharedPref.EVENT_NAME);
        getActivity().setTitle(event_id.equals("") ? TITLE : event_name);

        this.adapter = new EventListAdapter(activity, R.layout.listview_interaction);
        listview_eventlist = (ListView) activity.findViewById(R.id.listview_eventlist);
        listview_eventlist.setAdapter(adapter);
        listview_eventlist.setOnItemClickListener(this);

        searchview_eventlist = (SearchView) activity.findViewById(R.id.searchview_eventlist);
        searchview_eventlist.setOnQueryTextListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swiperefreshlayout_eventlist);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event item = (Event) parent.getItemAtPosition(position);
        Log.d("ITEM", item.getId());
        new FetchEventDetails(activity, item).execute();
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

    @Override
    public void onRefresh() {
        searchEvent();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void sortEvents() {
        adapter.sort();
    }
}
