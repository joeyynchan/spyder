package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.EventListAdapter;
import g1436218.com.spyder.asyncTask.JoinEvent;
import g1436218.com.spyder.object.Event;

public class EventFragment extends BaseMainFragment implements View.OnClickListener {

    private Event event;

    TextView textview_fragment_event_name ;
    TextView textview_fragment_event_host ;
    TextView textview_fragment_event_location;
    TextView textview_fragment_event_startTime;
    TextView textview_fragment_event_endTime;
    TextView textview_fragment_event_description;
    Button button_fragment_event_joinEvent;

    public EventFragment(MainActivity activity, Event event) {
        super(activity, R.layout.fragment_event);
        this.event = event;
    }

    @Override
    protected void initializeView() {
        textview_fragment_event_name = (TextView) activity.findViewById(R.id.textview_fragment_event_name);
        textview_fragment_event_host = (TextView) activity.findViewById(R.id.textview_fragment_event_host);
        textview_fragment_event_location = (TextView) activity.findViewById(R.id.textview_fragment_event_location);
        textview_fragment_event_startTime = (TextView) activity.findViewById(R.id.textview_fragment_event_startTime);
        textview_fragment_event_endTime = (TextView) activity.findViewById(R.id.textview_fragment_event_endTime);
        textview_fragment_event_description = (TextView) activity.findViewById(R.id.textview_fragment_event_description);
        button_fragment_event_joinEvent = (Button) activity.findViewById(R.id.button_fragment_event_joinEvent);
        button_fragment_event_joinEvent.setOnClickListener(this);

        textview_fragment_event_name.setText(event.getName());
        textview_fragment_event_location.setText(event.getLocation());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fragment_event_joinEvent: new JoinEvent(activity, event.getId()).execute();
            default: break;
        }
    }

}
