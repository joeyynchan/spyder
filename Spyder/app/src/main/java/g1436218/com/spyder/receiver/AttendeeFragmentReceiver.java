package g1436218.com.spyder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.fragment.AttendeeFragment;

public class AttendeeFragmentReceiver extends BroadcastReceiver {

    AttendeeFragment fragment;

    public AttendeeFragmentReceiver(AttendeeFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (MainActivity.UPDATE_ATTENDEE_FRAGMENT_ADAPTER.equals(action)) {
            fragment.clearAdapter();
            fragment.addAllToAdapter();
        }
    }
}
