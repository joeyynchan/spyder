package g1436218.com.spyder.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.asyncTask.FetchAttendee;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.service.BluetoothDiscovery;
import g1436218.com.spyder.service.GCMMessageHandler;

public class AttendeeFragment extends BaseMainFragment implements AdapterView.OnItemClickListener {

    private ListView listview_attendee;
    private AttendeeAdapter adapter;
    private UIUpdateReceiver receiver;
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
    public void onResume() {
        receiver = new UIUpdateReceiver(activity);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivity.UPDATE_ATTENDEE_FRAGMENT_ADAPTER);
        activity.registerReceiver(receiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        activity.unregisterReceiver(receiver);                  /* Unregister Receiver */
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Attendee item = (Attendee) parent.getItemAtPosition(position);
        new FetchUserProfile(activity, item.getUsername()).execute();
    }

    private class UIUpdateReceiver extends BroadcastReceiver {

        MainActivity activity;

        public UIUpdateReceiver(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MainActivity.UPDATE_ATTENDEE_FRAGMENT_ADAPTER.equals(action)) {
                adapter.clear();
                adapter.addAll(activity.getAttendees());
            }
        }
    }

}
