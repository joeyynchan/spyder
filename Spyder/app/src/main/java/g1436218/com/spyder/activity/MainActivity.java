package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayProfile;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.fragment.EventListFragment;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.fragment.LogoutFragment;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.Interactions;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.receiver.MainActivityReceiver;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity";

    private Intent bluetoothDiscoveryIntent;
    private InteractionPackage interactionPackage;
    private ArrayList<Attendee> attendees;
    private MainActivityReceiver receiver;
    private UserMap userMap;

    private LinearLayout button_attendee_list;
    private LinearLayout button_event_list;
    private LinearLayout button_interactions;
    private LinearLayout button_profile;

    private ImageView imageview_attendee_list;
    private ImageView imageview_event_list;
    private ImageView imageview_interactions;
    private ImageView imageview_profile;

    private TextView textview_attendee_list;
    private TextView textview_event_list;
    private TextView textview_interatcions;
    private TextView textview_profile;

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
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        showInteractions();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);                  /* Unregister Receiver */
        super.onStop();
    }

    @Override
    public void initializeView() {

        button_attendee_list = (LinearLayout) findViewById(R.id.button_attendee_list);
        button_interactions = (LinearLayout) findViewById(R.id.button_interactions);
        button_event_list = (LinearLayout) findViewById(R.id.button_event_list);
        button_profile = (LinearLayout) findViewById(R.id.button_profile);

        imageview_attendee_list = (ImageView) findViewById(R.id.button_attendee_list_icon);
        imageview_interactions = (ImageView) findViewById(R.id.button_interactions_icon);
        imageview_event_list = (ImageView) findViewById(R.id.button_event_list_icon);
        imageview_profile = (ImageView) findViewById(R.id.button_profile_icon);

        textview_attendee_list = (TextView) findViewById(R.id.button_attendee_list_text);
        textview_interatcions = (TextView) findViewById(R.id.button_interactions_text);
        textview_event_list = (TextView) findViewById(R.id.button_event_list_text);
        textview_profile = (TextView) findViewById(R.id.button_event_profile_text);

        button_attendee_list.setOnClickListener(this);
        button_event_list.setOnClickListener(this);
        button_interactions.setOnClickListener(this);
        button_profile.setOnClickListener(this);

        imageview_status = (ImageView) findViewById(R.id.main_activity_status);
        imageview_status.setOnClickListener(this);
        setStatus(BluetoothAdapter.getDefaultAdapter().getScanMode());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_start_bluetooth).setVisible(false);
        menu.findItem(R.id.action_stop_bluetooth).setVisible(false);
        menu.findItem(R.id.action_set_discoverable).setVisible(false);
        menu.findItem(R.id.action_start_discovery).setVisible(false);
        menu.findItem(R.id.action_stop_discovery).setVisible(false);

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
                logout(); break;
            case R.id.action_start_bluetooth:
                turnOnBluetooth(); break;
            case R.id.action_stop_bluetooth:
                turnOffBluetooth(); break;
            case R.id.action_set_discoverable:
                setDiscoverable(); break;
            case R.id.action_start_discovery:
                startDiscovery(); break;
            case R.id.action_stop_discovery:
                stopDiscovery(); break;
            default: break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FragmentManager fragmentManager = getFragmentManager();
        new LogoutFragment().show(fragmentManager, "Logout");
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_attendee_list: showAttendees(); break;
            case R.id.button_interactions: showInteractions(); break;
            case R.id.button_event_list: showEventList(); break;
            case R.id.button_profile: showProfile(); break;
            case R.id.main_activity_status: showStatus(); break;
            default: break;
        }
    }

    private void showAttendees() {
        new FetchAttendees(this).execute();
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof AttendeeFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AttendeeFragment(this), "CURRENT_FRAGMENT");
            fragmentTransaction.commit();
        }
        resetButtonState();
        imageview_attendee_list.setImageResource(R.drawable.main_activity_attendee_list_pressed);
        textview_attendee_list.setTextColor(getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    private void showEventList() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof EventListFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new EventListFragment(this), "CURRENT_FRAGMENT");
            fragmentTransaction.commit();
        }
        resetButtonState();
        imageview_event_list.setImageResource(R.drawable.main_activity_event_list_pressed);
        textview_event_list.setTextColor(getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    private void showInteractions() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof InteractionFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new InteractionFragment(this), "CURRENT_FRAGMENT");
            fragmentTransaction.commit();
        }
        resetButtonState();
        imageview_interactions.setImageResource(R.drawable.main_activity_interactions_icon_pressed);
        textview_interatcions.setTextColor(getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    private void showProfile() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String currUsername = sharedPref.getString(this.getString(R.string.username), "");
        new FetchUserProfile(this, currUsername).execute();
        resetButtonState();
        imageview_profile.setImageResource(R.drawable.main_activity_profile_pressed);
        textview_profile.setTextColor(getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    private void showStatus() {
        Toast.makeText(getApplicationContext(), "Status", Toast.LENGTH_LONG).show();
    }

    private void resetButtonState() {
        imageview_attendee_list.setImageResource(R.drawable.main_activity_attendee_list_normal);
        imageview_event_list.setImageResource(R.drawable.main_activity_event_list_normal);
        imageview_interactions.setImageResource(R.drawable.main_activity_interactions_icon_normal);
        imageview_profile.setImageResource(R.drawable.main_activity_profile_normal);

        textview_attendee_list.setTextColor(getResources().getColor(R.color.textedit_background));
        textview_event_list.setTextColor(getResources().getColor(R.color.textedit_background));
        textview_interatcions.setTextColor(getResources().getColor(R.color.textedit_background));
        textview_profile.setTextColor(getResources().getColor(R.color.textedit_background));
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
        stopService(bluetoothDiscoveryIntent);      /* Stop BluetoothDiscovery Service */
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

    /* Turn off Bluetooth. Stop discovery if there exists one */
    public void turnOffBluetooth() {
        BluetoothAdapter.getDefaultAdapter().disable();
        if (discovery) {
            stopDiscovery();
        }
    }

    public void setStatus(int status) {
        switch (status) {
            case 1:
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
