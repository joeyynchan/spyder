package g1436218.com.spyder.fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.asyncTask.DisplayAttendeeProfile;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.intentfilter.AttendeeFragmentIntentFilter;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.receiver.AttendeeFragmentReceiver;

public class AttendeeFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView listview_attendee;
    private TextView textview_name;
    private TextView textview_msg;
    private AttendeeAdapter adapter;
    private SearchView searchview_attendee;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final String TITLE = "Attendee List";

    public AttendeeFragment(MainActivity activity) {
        super(activity, R.layout.fragment_attendee);
    }

    @Override
    protected void initializeView() {

        String event_id = activity.getSharedPrefString(SharedPref.EVENT_ID);
        String event_name = activity.getSharedPrefString(SharedPref.EVENT_NAME);

        getActivity().setTitle(event_id.equals("") ? TITLE : event_name);
        adapter = new AttendeeAdapter(activity, R.layout.listview_attendees);

        listview_attendee = (ListView) getActivity().findViewById(R.id.listview_attendee);
        listview_attendee.setAdapter(adapter);
        listview_attendee.setOnItemClickListener(this);

        searchview_attendee = (SearchView) getActivity().findViewById(R.id.searchview_attendee);
        searchview_attendee.setOnQueryTextListener(this);
        searchAttendees();

        textview_name = (TextView) activity.findViewById(R.id.textview_fragment_attendee_eventName);
        textview_msg = (TextView) activity.findViewById(R.id.textview_fragment_attendee_eventMsg);

        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swiperefreshlayout_attendee);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setOnRefreshListener(this);

        if (!event_id.equals("")) {
            //textview_name.setText(event_name);
            activity.setTitle(event_name);
            textview_msg.setVisibility(View.GONE);
        } else {
            textview_msg.setText("There is no ongoing event.");
            //textview_name.setVisibility(View.GONE);
            listview_attendee.setVisibility(View.GONE);
        }
    }

    public void registerReceiver() {
        receiver = new AttendeeFragmentReceiver(this);
        activity.registerReceiver(receiver, new AttendeeFragmentIntentFilter());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Attendee item = (Attendee) parent.getItemAtPosition(position);
        new DisplayAttendeeProfile(activity, item.getUsername()).execute();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchAttendees();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchAttendees();
        return false;
    }

    public void searchAttendees() {
        adapter.clear();
        ArrayList<Attendee> list = new ArrayList<Attendee>(activity.getAttendees().values());
        Collections.sort(list, new SortAttendeesByUsername());
        String keyword = searchview_attendee.getQuery().toString();
        if (!keyword.equals("")) {
            for (Attendee attendee : list) {
                if (attendee.containKeyword(keyword)) {
                    adapter.add(attendee);
                }
            }
        } else {
            adapter.addAll(list);
        }
    }

    @Override
    public void onRefresh() {
        new FetchAttendees(activity).execute();
        swipeRefreshLayout.setRefreshing(false);
    }

    private class SortAttendeesByUsername implements Comparator<Attendee> {
        @Override
        public int compare(Attendee lhs, Attendee rhs) {
            if (lhs.getName().compareTo(rhs.getName()) < 0) {
                return -1;
            } else if (lhs.getName().compareTo(rhs.getName()) > 0) {
                return 1;
            }
            return 0;
        }
    }

}
