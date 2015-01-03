package g1436218.com.spyder.fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.internal.util.Predicate;

import java.util.Collections;
import java.util.Comparator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.receiver.AttendeeFragmentReceiver;

public class AttendeeFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

    private ListView listview_attendee;
    private TextView textview_name;
    private AttendeeAdapter adapter;
    private SearchView searchview_attendee;
    private final String TITLE = "Attendee List";

    public AttendeeFragment(MainActivity activity) {
        super(activity, R.layout.fragment_attendee);
    }

    @Override
    protected void initializeView() {
        getActivity().setTitle(TITLE);
        adapter = new AttendeeAdapter(activity, R.layout.listview_attendees);

        listview_attendee = (ListView) getActivity().findViewById(R.id.listview_attendee);
        listview_attendee.setAdapter(adapter);
        listview_attendee.setOnItemClickListener(this);

        searchview_attendee = (SearchView) getActivity().findViewById(R.id.searchview_attendee);
        searchview_attendee.setOnQueryTextListener(this);
        searchAttendees("");

        textview_name = (TextView) activity.findViewById(R.id.textview_fragment_attendee_eventName);
        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String event_id = sharedPref.getString("EVENT_ID", "");
        String event_name = sharedPref.getString("EVENT_NAME", "");

        if (!event_id.equals("")) {
            textview_name.setText(event_name);
        } else {
            textview_name.setText("No event is happening");
        }
    }

    public void registerReceiver() {
        AttendeeFragmentReceiver _receiver = new AttendeeFragmentReceiver(this);
        _receiver.setAttendees(adapter);
        receiver = _receiver;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.UPDATE_ATTENDEE_FRAGMENT_ADAPTER);
        activity.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Attendee item = (Attendee) parent.getItemAtPosition(position);
        new FetchUserProfile(activity, item.getUsername()).execute();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchAttendees(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String query = searchview_attendee.getQuery().toString();
        searchAttendees(query);
        return false;
    }

    private void searchAttendees(String keyword) {
        adapter.clear();
        Collections.sort(activity.getAttendees(), new SortAttendeesByUsername());
        if (!keyword.equals("")) {
            for (Attendee attendee : activity.getAttendees()) {
                if (attendee.containKeyword(keyword)) {
                    adapter.add(attendee);
                }
            }
        } else {
            adapter.addAll(activity.getAttendees());
        }
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
