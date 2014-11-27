package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Event;
import g1436218.com.spyder.object.UserMap;

public class AttendeeFragment extends BaseMainFragment implements AdapterView.OnItemClickListener {

    private ListView listview_attendee;
    private AttendeeAdapter attendeeAdapter;
    private final String TITLE = "Attendee List";

    public AttendeeFragment(MainActivity activity) {
        super(activity, R.layout.fragment_attendee);
    }

    @Override
    protected void initializeView() {
        getActivity().setTitle(TITLE);
        attendeeAdapter = new AttendeeAdapter(getActivity(), R.layout.listview_attendees);
        attendeeAdapter.addAll(activity.getAttendees());
        listview_attendee = (ListView) getActivity().findViewById(R.id.listview_attendee);
        listview_attendee.setAdapter(attendeeAdapter);
        listview_attendee.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Attendee item = (Attendee) parent.getItemAtPosition(position);
        new FetchUserProfile((MainActivity) getActivity(), item.getUsername()).execute();
    }
}
