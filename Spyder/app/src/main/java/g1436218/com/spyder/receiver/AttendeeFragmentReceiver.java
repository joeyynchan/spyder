package g1436218.com.spyder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.object.Action;

public class AttendeeFragmentReceiver extends BroadcastReceiver {

    AttendeeFragment fragment;
    private AttendeeAdapter adapter;


    public AttendeeFragmentReceiver(AttendeeFragment fragment) {
        this.fragment = fragment;
    }

    public void setAttendees(AttendeeAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Action.UPDATE_ATTENDEE_FRAGMENT_ADAPTER.equals(action)) {
            fragment.searchAttendees();
        }
    }
}
