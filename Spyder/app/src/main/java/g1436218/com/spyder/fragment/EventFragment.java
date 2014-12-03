package g1436218.com.spyder.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.JoinEvent;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Event;

public class EventFragment extends BaseMainFragment implements View.OnClickListener {

    private Event event;

    TextView textview_name;
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
        textview_name = (TextView) activity.findViewById(R.id.textview_fragment_event_name);
        textview_host = (TextView) activity.findViewById(R.id.textview_fragment_event_host);
        textview_location = (TextView) activity.findViewById(R.id.textview_fragment_event_location);
        textview_startTime = (TextView) activity.findViewById(R.id.textview_fragment_event_startTime);
        textview_endTime = (TextView) activity.findViewById(R.id.textview_fragment_event_endTime);
        textview_description = (TextView) activity.findViewById(R.id.textview_fragment_event_description);

        button_setToCurrentEvent = (Button) activity.findViewById(R.id.button_fragment_event_setToCurrentEvent);
        button_setToCurrentEvent.setOnClickListener(this);
        
        button_joinEvent = (Button) activity.findViewById(R.id.button_fragment_event_joinEvent);
        button_joinEvent.setOnClickListener(this);
        if (event.getStatus().equals("Attending")) {
            button_joinEvent.setVisibility(View.GONE);
            button_setToCurrentEvent.setVisibility(View.VISIBLE);
        }
        textview_name.setText(event.getName());
        checkCurrentEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fragment_event_joinEvent: new JoinEvent(activity, event.getId()).execute(); break;
            case R.id.button_fragment_event_setToCurrentEvent: setToCurrentEvent(); break;
            default: break;
        }
    }

    private void setToCurrentEvent() {
        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EVENT_ID", event.getId());
        editor.putString("EVENT_NAME", event.getName());
        editor.commit();
        checkCurrentEvent();
    }

    private void checkCurrentEvent() {
        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String event_id = sharedPref.getString("EVENT_ID", "");
        if (event_id.equals(event.getId())) {
            button_setToCurrentEvent.setClickable(false);
            button_setToCurrentEvent.setText("Current Event");
        } else {
            button_setToCurrentEvent.setClickable(true);
            button_setToCurrentEvent.setText("Set to Current Event");
        }
    }

}
