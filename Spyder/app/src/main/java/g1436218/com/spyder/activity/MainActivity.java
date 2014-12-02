package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayProfile;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.asyncTask.GetRegisterId;
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
    private LinearLayout button_interactions;
    private LinearLayout button_event_list;

    private ImageView imageview_attendee_list;
    private ImageView imageview_interactions;
    private ImageView imageview_event_list;

    private TextView textview_attendee_list;
    private TextView textview_interatcions;
    private TextView textview_event_list;

    private int nid = 0;

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
        registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onStart() {
        new GetRegisterId(this).execute();
        super.onStart();
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

        imageview_attendee_list = (ImageView) findViewById(R.id.button_attendee_list_icon);
        imageview_interactions = (ImageView) findViewById(R.id.button_interactions_icon);
        imageview_event_list = (ImageView) findViewById(R.id.button_event_list_icon);

        textview_attendee_list = (TextView) findViewById(R.id.button_attendee_list_text);
        textview_interatcions = (TextView) findViewById(R.id.button_interactions_text);
        textview_event_list = (TextView) findViewById(R.id.button_event_list_text);


        button_attendee_list.setOnClickListener(this);
        button_interactions.setOnClickListener(this);
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
            case R.id.button_attendee_list: showAttendees(); break;
            case R.id.button_interactions: showInteractions(); break;
            case R.id.button_event_list: showEventList(); break;
            case R.id.imagebutton_activity_main_profile: new DisplayProfile(this).execute(); break;
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

    private void resetButtonState() {
        imageview_attendee_list.setImageResource(R.drawable.main_activity_attendee_list_normal);
        imageview_event_list.setImageResource(R.drawable.main_activity_event_list_normal);
        imageview_interactions.setImageResource(R.drawable.main_activity_interactions_icon_normal);

        textview_attendee_list.setTextColor(getResources().getColor(R.color.textedit_background));
        textview_event_list.setTextColor(getResources().getColor(R.color.textedit_background));
        textview_interatcions.setTextColor(getResources().getColor(R.color.textedit_background));
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

    public void startBluetoothDiscoveryService() {
        turnOnBluetooth();
        bluetoothDiscoveryIntent = new Intent(getBaseContext(), BluetoothDiscovery.class);
        startService(bluetoothDiscoveryIntent);
    }

    public void stopBluetoothDiscoveryService() {
        stopService(bluetoothDiscoveryIntent);      /* Stop BluetoothDiscovery Service */
    }

    public void turnOnBluetooth() {
            /* Set Device always discoverable */
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(discoverableIntent);
    }

    public void turnOffBluetooth() {
        BluetoothAdapter.getDefaultAdapter().disable();
    }

    public void addGCMToClipBoard(String regid) {
        ClipData clip = ClipData.newPlainText("gcm", regid);
        ClipboardManager clipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipBoard.setPrimaryClip(clip);
    }
}
