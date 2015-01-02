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
import g1436218.com.spyder.object.BluetoothController;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.Interactions;
import g1436218.com.spyder.object.UIController;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.receiver.MainActivityReceiver;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity";

    private UserMap userMap;
    private ArrayList<Attendee> attendees;
    private InteractionPackage interactionPackage;
    private MainActivityReceiver receiver;
    private UIController uiController;
    private BluetoothController bluetoothController;

    private LinearLayout button_attendee_list;
    private LinearLayout button_event_list;
    private LinearLayout button_interactions;
    private LinearLayout button_profile;

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

        bluetoothController = new BluetoothController(this);
        new FetchAttendees(this).execute();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);                  /* Unregister Receiver */
        super.onDestroy();
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
            if (bluetoothController.isDiscovery()) {
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
                uiController.logout();
                break;
            case R.id.action_start_bluetooth:
                bluetoothController.turnOnBluetooth();
                break;
            case R.id.action_stop_bluetooth:
                bluetoothController.turnOffBluetooth();
                break;
            case R.id.action_set_discoverable:
                bluetoothController.setDiscoverable();
                break;
            case R.id.action_start_discovery:
                bluetoothController.startDiscovery();
                break;
            case R.id.action_stop_discovery:
                bluetoothController.stopDiscovery();
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

    }

    public InteractionPackage getInteractionPackage() {
        return interactionPackage;
    }

    public BluetoothController getBluetoothController() {
        return bluetoothController;
    }

    public UIController getUIController() {
        return uiController;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

}