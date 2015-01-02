package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayProfile;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.fragment.EventListFragment;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.fragment.LogoutFragment;
import g1436218.com.spyder.fragment.ProfileFragment;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.Interactions;
import g1436218.com.spyder.object.UIController;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.receiver.MainActivityReceiver;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity";
    public static final int DISCOVERY_ON = 1;

    private Intent bluetoothDiscoveryIntent;
    private InteractionPackage interactionPackage;
    private ArrayList<Attendee> attendees;
    private MainActivityReceiver receiver;
    private UserMap userMap;
    private UIController uiController;

    private LinearLayout button_attendee_list;
    private LinearLayout button_event_list;
    private LinearLayout button_interactions;
    private LinearLayout button_profile;

    private ImageView imageview_status;
    private int nid = 0;
    private boolean discovery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        interactionPackage = new InteractionPackage();
        attendees = new ArrayList<Attendee>();

        /* Register UIUpdateReceiver */
        receiver = new MainActivityReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.DEVICE_DETECTED);
        intentFilter.addAction(Action.RESET_LIST);
        intentFilter.addAction(Action.SEND_DATA);
        intentFilter.addAction(Action.START_DISCOVERY);
        intentFilter.addAction(Action.STOP_DISCOVERY);
        intentFilter.addAction(Action.START_BLUETOOTH);
        intentFilter.addAction(Action.STOP_BLUETOOTH);
        intentFilter.addAction(Action.FETCH_ATTENDEES);
        intentFilter.addAction(Action.GET_GCM);
        intentFilter.addAction(Action.UPDATE_CURRENT_EVENT);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);

        uiController = new UIController(this);
        uiController.showInteractions();
        new FetchAttendees(this).execute();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);                  /* Unregister Receiver */
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptions");
        menu.findItem(R.id.action_start_bluetooth).setVisible(false);
        menu.findItem(R.id.action_stop_bluetooth).setVisible(false);
        menu.findItem(R.id.action_set_discoverable).setVisible(false);
        menu.findItem(R.id.action_start_discovery).setVisible(false);
        menu.findItem(R.id.action_stop_discovery).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_done).setVisible(false);

        if (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_OFF) {
            menu.findItem(R.id.action_start_bluetooth).setVisible(true);
        } else {
            menu.findItem(R.id.action_stop_bluetooth).setVisible(true);
        }
        if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE) {
            menu.findItem(R.id.action_set_discoverable).setVisible(true);
        }
        if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            if (discovery) {
                menu.findItem(R.id.action_stop_discovery).setVisible(true);
            } else {
                menu.findItem(R.id.action_start_discovery).setVisible(true);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                logout();
                break;
            case R.id.action_start_bluetooth:
                turnOnBluetooth();
                break;
            case R.id.action_stop_bluetooth:
                turnOffBluetooth();
                break;
            case R.id.action_set_discoverable:
                setDiscoverable();
                break;
            case R.id.action_start_discovery:
                startDiscovery();
                break;
            case R.id.action_stop_discovery:
                stopDiscovery();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_attendee_list:
                uiController.showAttendees();
                break;
            case R.id.button_interactions:
                uiController.showInteractions();
                break;
            case R.id.button_event_list:
                uiController.showEventList();
                break;
            case R.id.button_profile:
                uiController.showProfile();
                break;
            case R.id.main_activity_status:
                showStatus();
                break;
            default:
                break;
        }
    }

    @Override
    public void initializeView() {

        button_attendee_list = (LinearLayout) findViewById(R.id.button_attendee_list);
        button_interactions = (LinearLayout) findViewById(R.id.button_interactions);
        button_event_list = (LinearLayout) findViewById(R.id.button_event_list);
        button_profile = (LinearLayout) findViewById(R.id.button_profile);

        button_attendee_list.setOnClickListener(this);
        button_event_list.setOnClickListener(this);
        button_interactions.setOnClickListener(this);
        button_profile.setOnClickListener(this);

        imageview_status = (ImageView) findViewById(R.id.main_activity_status);
        imageview_status.setOnClickListener(this);
        setStatus(BluetoothAdapter.getDefaultAdapter().getScanMode());
    }

    private void logout() {
        FragmentManager fragmentManager = getFragmentManager();
        new LogoutFragment().show(fragmentManager, "Logout");
    }

    private void showStatus() {
        Toast.makeText(getApplicationContext(), "Status", Toast.LENGTH_LONG).show();
    }



    /* Manipulate InteractionPackage */

    public Interactions getInteractions() {
        return interactionPackage.getInteractions();
    }

    public Interactions getClone() {
        return interactionPackage.getClone();
    }

    public InteractionPackage getInteractionPackage() {
        return interactionPackage;
    }

    public void addToInteractions(Interaction interaction) {
        interactionPackage.addInteraction(interaction);
    }

    /* RESET LIST is performed when a discovery session finishes.
     * 1) Add Interactions to package
     * 2) Clone interactions for InteractionFragment
     * 3) Clear interactions
     * 4) Tell InteractionFragment to update using clone
    */
    public void addInteractionsToPackage() {
        interactionPackage.addInteractionsToPackage();
        interactionPackage.copyInteractionsToClone();
        interactionPackage.createInteractions();
        broadcastUpdateAdapter();
    }

    public void clearArray() {
        interactionPackage.clear();
    }

    public boolean isPackageEmpty() {
        return interactionPackage.isPackageEmpty();
    }

    private void broadcastUpdateAdapter() {
        Intent intent = new Intent();
        intent.setAction(Action.UPDATE_INTERACTION_FRAGMENT_ADAPTER);
        sendBroadcast(intent);
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

    /* Bluetooth Discovery Service */

    /* Starts discovery only when the device is discoverable and connectable */
    public void startDiscovery() {
        if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            bluetoothDiscoveryIntent = new Intent(getBaseContext(), BluetoothDiscovery.class);
            startService(bluetoothDiscoveryIntent);
            setStatus(1);
            discovery = true;
        }
    }

    public void stopDiscovery() {
        stopService(bluetoothDiscoveryIntent);
        setStatus(BluetoothAdapter.getDefaultAdapter().getScanMode());
        discovery = false;
    }

    public void turnOnBluetooth() {
        BluetoothAdapter.getDefaultAdapter().enable();
    }

    public void setDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(discoverableIntent);
    }

    public void turnOffBluetooth() {
        BluetoothAdapter.getDefaultAdapter().disable();
        if (discovery) {
            stopDiscovery();
        }
    }

    public void setStatus(int status) {
        switch (status) {
            case DISCOVERY_ON:
                imageview_status.setImageResource(R.drawable.main_activity_status_discovery_on);
                break;
            case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                imageview_status.setImageResource(R.drawable.main_activity_status_discoverable);
                break;
            case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                imageview_status.setImageResource(R.drawable.main_activity_status_discovering);
                break;
            default:
                imageview_status.setImageResource(R.drawable.main_activity_status_normal);
                break;
        }
    }

}