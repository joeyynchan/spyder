package g1436218.com.spyder.fragment;

import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.receiver.AttendeeFragmentReceiver;

public class AttendeeFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener {

    private ListView listview_attendee;
    private AttendeeAdapter adapter;
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
        adapter.addAll(activity.getAttendees());
        listview_attendee.setOnItemClickListener(this);
    }

    public void registerReceiver() {
        receiver = new AttendeeFragmentReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.UPDATE_ATTENDEE_FRAGMENT_ADAPTER);
        activity.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Attendee item = (Attendee) parent.getItemAtPosition(position);
        new FetchUserProfile(activity, item.getUsername()).execute();
    }

    public void clearAdapter() {
        adapter.clear();
    }

    public void addAllToAdapter() {
        adapter.addAll(activity.getAttendees());
    }

}
