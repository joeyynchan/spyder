package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.EventListAdapter;
import g1436218.com.spyder.object.Event;

/**
 * Created by Joey on 24/11/2014.
 */
public class EventFragment extends BaseFragment {

    private MainActivity activity;
    private Event event;


    TextView textview_fragment_event_name ;
    TextView textview_fragment_event_host ;
    TextView textview_fragment_event_location;
    TextView textview_fragment_event_startTime;
    TextView textview_fragment_event_endTime;
    TextView textview_fragment_event_description;

    public EventFragment(MainActivity activity, Event event) {
        super(activity);
        this.event = event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onResume() {
        textview_fragment_event_name = (TextView) getActivity().findViewById(R.id.textview_fragment_event_name);
        textview_fragment_event_name.setText(event.getName());
        textview_fragment_event_host = (TextView) getActivity().findViewById(R.id.textview_fragment_event_host);
        textview_fragment_event_location = (TextView) getActivity().findViewById(R.id.textview_fragment_event_location);
        textview_fragment_event_location.setText(event.getLocation());
        textview_fragment_event_startTime = (TextView) getActivity().findViewById(R.id.textview_fragment_event_startTime);
        textview_fragment_event_endTime = (TextView) getActivity().findViewById(R.id.textview_fragment_event_endTime);
        textview_fragment_event_description = (TextView) getActivity().findViewById(R.id.textview_fragment_event_description);
        super.onResume();
    }

}
