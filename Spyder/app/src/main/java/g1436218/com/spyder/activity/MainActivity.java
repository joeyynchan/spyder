package g1436218.com.spyder.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.CheckConnection;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.intentfilter.MainActivityIntentFilter;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.BluetoothController;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.UIController;
import g1436218.com.spyder.object.Attendees;
import g1436218.com.spyder.receiver.MainActivityReceiver;


public class MainActivity extends BaseActivity {

    /*
     *  MainActivity contains 2 controllers: UIController & BluetoothController
     *
     *  UIController: it controls all the UI component in MainActivity including OptionMenu.
     *
     *  BluetoothController: It controls the BluetoothAdapter and the discovery status of the
     *  device.This controller depends on UIController, therefore it must be declared after
     *  UIController or set set the UIController manually
     *
     *  Attendees: A list of attendees of the current event(current event can be null);
     *
     *  InteractionPackage: A package containing all the sumbiting data.
     *
     *  MainActivityReceiver: A receiver responsible for all the action related to MainActivity
     *
     */

    private final String TAG = "MainActivity";

    private Attendees attendees;
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
        attendees = Attendees.getInstance();

        uiController = new UIController(this);
        uiController.showInteractions();

        bluetoothController = new BluetoothController(this);

        /* Register UIUpdateReceiver */
        receiver = new MainActivityReceiver(this);
        registerReceiver(receiver, new MainActivityIntentFilter());

        new FetchAttendees(this).execute();
        new CheckConnection(this).execute();

    }

    @Override
    protected void onDestroy() {
        /* Unregister Receiver */
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
       return uiController.showOptionMenu(menu);
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
            Log.d("CHECKING FRAGMENT", getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT").getClass().toString());
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

        /* Interactions */
        button_interactions = (LinearLayout) findViewById(R.id.button_interactions);
        button_interactions.setOnClickListener(this);

        /* Attendee List */
        button_attendee_list = (LinearLayout) findViewById(R.id.button_attendee_list);
        button_attendee_list.setOnClickListener(this);

        /* Events */
        button_event_list = (LinearLayout) findViewById(R.id.button_event_list);
        button_event_list.setOnClickListener(this);

        /* Profile */
        button_profile = (LinearLayout) findViewById(R.id.button_profile);
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

    public Attendees getAttendees() {
        return attendees;
    }

    public void setToCurrentEvent(String event_id, String event_name) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EVENT_ID", event_id);
        editor.putString("EVENT_NAME", event_name);
        editor.commit();
        Log.d("Event ID", event_id);
        Log.d("Event Name", event_name);
        new FetchAttendees(this).execute();
    }

}