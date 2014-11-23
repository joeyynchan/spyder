package g1436218.com.spyder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.object.UserMap;

public class AttendeeFragment extends BaseFragment {

    private ListView listview_attendee;
    private AttendeeAdapter attendeeAdapter;
    private final String TITLE = "Attendee List";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendee, container, false);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(TITLE);
        attendeeAdapter = new AttendeeAdapter(getActivity(), R.layout.listview_attendees);
        listview_attendee = (ListView) getActivity().findViewById(R.id.listview_attendee);
        listview_attendee.setAdapter(attendeeAdapter);

        super.onResume();
    }

}
