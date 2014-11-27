package g1436218.com.spyder.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;

public class AttendeeFragment extends BaseMainFragment implements AdapterView.OnItemClickListener {

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Attendee item = (Attendee) parent.getItemAtPosition(position);
        new FetchUserProfile(activity, item.getUsername()).execute();
    }
}
