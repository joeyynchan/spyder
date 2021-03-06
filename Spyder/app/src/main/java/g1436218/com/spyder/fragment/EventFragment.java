package g1436218.com.spyder.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.asyncTask.JoinEvent;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.object.Event;

public class EventFragment extends BaseMainFragment implements View.OnClickListener {

    private Event event;
    private static String TAG = "EventFragment";
    TextView textview_host;
    TextView textview_location;
    TextView textview_startTime;
    TextView textview_endTime;
    TextView textview_description;
    Button button_joinEvent;
    Button button_setToCurrentEvent;

    public EventFragment(MainActivity activity, Event event) {
        super(activity, R.layout.fragment_event);
        this.event = event;
    }

    @Override
    protected void initializeView() {
        Log.d(TAG, "initializeView");
        textview_host = (TextView) activity.findViewById(R.id.textview_fragment_event_host);
        textview_location = (TextView) activity.findViewById(R.id.textview_fragment_event_location);
        textview_startTime = (TextView) activity.findViewById(R.id.textview_fragment_event_startTime);
        textview_endTime = (TextView) activity.findViewById(R.id.textview_fragment_event_endTime);
        textview_description = (TextView) activity.findViewById(R.id.textview_fragment_event_description);

        //textview_name.setText(event.getName());
        activity.setTitle(event.getName());
        textview_host.setText(event.getName());
        textview_location.setText(event.getLocation());
        textview_startTime.setText(event.getStartTime());
        textview_endTime.setText(event.getEndTime());
        textview_description.setText(event.getDescription());

        button_setToCurrentEvent = (Button) activity.findViewById(R.id.button_fragment_event_setToCurrentEvent);
        button_setToCurrentEvent.setOnClickListener(this);
        
        button_joinEvent = (Button) activity.findViewById(R.id.button_fragment_event_joinEvent);
        button_joinEvent.setOnClickListener(this);
        if (event.getStatus().equals("Attending")) {
            button_joinEvent.setVisibility(View.GONE);
            button_setToCurrentEvent.setVisibility(View.VISIBLE);
        }
        checkCurrentEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fragment_event_joinEvent: {
                new JoinEvent(this, event.getId()).execute();
            } break;
            case R.id.button_fragment_event_setToCurrentEvent: {
                activity.setToCurrentEvent(event.getId(), event.getName());
                checkCurrentEvent();
            } break;
            default: break;
        }
    }


    private void checkCurrentEvent() {
        String event_id = activity.getSharedPrefString(SharedPref.EVENT_ID);
        if (event_id.equals(event.getId())) {
            button_setToCurrentEvent.setClickable(false);
            button_setToCurrentEvent.setText("Current Event");
        } else {
            button_setToCurrentEvent.setClickable(true);
            button_setToCurrentEvent.setText("Set to Current Event");
        }
    }

}
