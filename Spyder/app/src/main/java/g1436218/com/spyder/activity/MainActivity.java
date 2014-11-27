package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.adapter.AttendeeAdapter;
import g1436218.com.spyder.asyncTask.DisplayMacAddress;
import g1436218.com.spyder.asyncTask.FetchAttendee;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.fragment.EventListFragment;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.fragment.LogoutFragment;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.Interactions;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends BaseActivity {


    private final String TAG = "MainActivity";

    private Intent bluetoothDiscoveryIntent;
    private InteractionPackage interactionPackage;
    private ArrayList<Attendee> attendees;
    private UIUpdateReceiver receiver;
    private UserMap userMap;

    private Button button_attendee;
    private Button button_interaction;
    private Button button_event_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        interactionPackage = new InteractionPackage();
        attendees = new ArrayList<Attendee>();

        /* Register UIUpdateReceiver */
        receiver = new UIUpdateReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDiscovery.DEVICE_DETECTED);
        intentFilter.addAction(BluetoothDiscovery.RESET_LIST);
        intentFilter.addAction(BluetoothDiscovery.SEND_DATA);
        intentFilter.addAction(BluetoothDiscovery.SET_DISCOVERABLE);
        registerReceiver(receiver, intentFilter);

        /*Display Device Information */
        new DisplayMacAddress(this).execute();
        new FetchAttendee(this).execute();

    }

    /* BluetoothDiscovery Service should be turned on when MainActivity is at the front */
    @Override
    protected void onStart() {
        /* Start BluetoothDiscovery Service */
        bluetoothDiscoveryIntent = new Intent(getBaseContext(), BluetoothDiscovery.class);
        startService(bluetoothDiscoveryIntent);

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(discoverableIntent);
        super.onStart();
    }

    /* Turns off BluetoothDiscovery when switching from MainAcitivity to other activities */
    @Override
    protected void onDestroy() {
        stopService(bluetoothDiscoveryIntent);      /* Stop BluetoothDiscovery Service */
        unregisterReceiver(receiver);                  /* Unregister Receiver */
        super.onStop();
    }

    @Override
    public void initializeView() {

        button_attendee = (Button) findViewById(R.id.button_attendee);
        button_interaction = (Button) findViewById(R.id.button_interaction);
        button_event_list = (Button) findViewById(R.id.button_event_list);

        button_attendee.setOnClickListener(this);
        button_interaction.setOnClickListener(this);
        button_event_list.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                logout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        FragmentManager fragmentManager = getFragmentManager();
        new LogoutFragment().show(fragmentManager, "Logout");
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
            setTitle("Spyder");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_attendee: showAttendees(); break;
            case R.id.button_interaction: showInteractions(); break;
            case R.id.button_event_list: showEventList(); break;
            default: break;
        }
    }

    private void showAttendees() {
        new FetchAttendee(this).execute();
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof AttendeeFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AttendeeFragment(this), "CURRENT_FRAGMENT");
            getFragmentManager().popBackStack();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void showInteractions() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof InteractionFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new InteractionFragment(this), "CURRENT_FRAGMENT");
            getFragmentManager().popBackStack();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void showEventList() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof EventListFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new EventListFragment(this), "CURRENT_FRAGMENT");
            getFragmentManager().popBackStack();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }



    private class UIUpdateReceiver extends BroadcastReceiver {

        MainActivity activity;

        public UIUpdateReceiver(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDiscovery.DEVICE_DETECTED.equals(action)) {
                String username = intent.getStringExtra("USERNAME");
                int strength = intent.getIntExtra("STRENGTH", 0);
                activity.addToInteractions(new Interaction(username, strength));
            } else if (BluetoothDiscovery.RESET_LIST.equals(action)) {
                Log.i(TAG, interactionPackage.getInteractions().toString());
                activity.addInteractionsToPackage();
            } else if (BluetoothDiscovery.SEND_DATA.equals(action)) {
                if (!isPackageEmpty()) {
                    new SubmitBluetoothData(activity, activity.getInteractionPackage()).execute();
                } else {
                    clearArray();
                }
            }
        }
    }



    /* Manipulate InteractionPackage */

    public Interactions getInteractions() {
        return interactionPackage.getInteractions();
    }

    public InteractionPackage getInteractionPackage() {
        return interactionPackage;
    }

    public void addToInteractions(Interaction interaction) {
        interactionPackage.addInteraction(interaction);
    }

    public void addInteractionsToPackage() {
        interactionPackage.addInteractionsToPackage();
        interactionPackage.createInteractions();
    }

    public void clearArray() {
        interactionPackage.clear();
    }

    public boolean isPackageEmpty() {
        return interactionPackage.isPackageEmpty();
    }

    /* Manipulate Attendess */

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    public void clearAttendees() {
        attendees.clear();
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

}
