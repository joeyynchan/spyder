package g1436218.com.spyder.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.intentfilter.StartBluetoothIntentFilter;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.BluetoothController;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.UIController;
import g1436218.com.spyder.object.Attendees;

public class MainActivityReceiver extends BroadcastReceiver {

    MainActivity activity;
    InteractionPackage interactionPackage;
    BluetoothController bluetoothController;
    UIController uiController;
    StartBluetoothReceiver receiver;
    boolean receiver_registered;

    public MainActivityReceiver(MainActivity activity) {
        this.activity = activity;
        this.interactionPackage = activity.getInteractionPackage();
        this.bluetoothController = activity.getBluetoothController();
        this.uiController = activity.getUIController();
        this.receiver = new StartBluetoothReceiver(activity);
        this.receiver_registered = false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (Action.DEVICE_DETECTED.equals(action)) {
            String macAddress = intent.getStringExtra("MAC_ADDRESS");
            int strength = intent.getIntExtra("STRENGTH", 0);
            Attendees usermap = Attendees.getInstance();
            interactionPackage.addInteraction(new Interaction(usermap.get(macAddress), strength));

        } else if (Action.RESET_LIST.equals(action)) {

            /* RESET LIST is performed when a discovery session finishes.
             * 1) Add Interactions to package
             * 2) Clone interactions for InteractionFragment
             * 3) Clear interactions
             * 4) Tell InteractionFragment to update using clone
             */

            interactionPackage.addInteractionsToPackage();
            interactionPackage.copyInteractionsToClone();
            interactionPackage.createInteractions();

            Intent _intent = new Intent();
            _intent.setAction(Action.UPDATE_INTERACTION_FRAGMENT_ADAPTER);
            activity.sendBroadcast(_intent);

        } else if (Action.SEND_DATA.equals(action)) {

            if (!interactionPackage.isPackageEmpty()) {
                new SubmitBluetoothData(activity).execute();
            } else {
                interactionPackage.clear();
            }

        } else if (Action.START_DISCOVERY.equals(action)) {
            bluetoothController.startDiscovery();
            if (receiver_registered) {
                activity.unregisterReceiver(receiver);
            }

        } else if (Action.STOP_DISCOVERY.equals(action)) {
            bluetoothController.stopDiscovery();

        } else if (Action.FETCH_ATTENDEES.equals(action)) {
            new FetchAttendees(activity).execute();

        } else if (Action.START_BLUETOOTH.equals(action)) {
            bluetoothController.turnOnBluetooth();

        } else if (Action.STOP_BLUETOOTH.equals(action)) {
            bluetoothController.turnOffBluetooth();

        } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {

            if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                uiController.setStatus(BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
            } else if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE) {
                uiController.setStatus(BluetoothAdapter.SCAN_MODE_CONNECTABLE);
            } else {
                uiController.setStatus(0);
            }

        } else if (Action.SHOW_MESSAGE.equals(action)) {
            String title = intent.getStringExtra("title");
            String message = intent.getStringExtra("message");
            String sender = intent.getStringExtra("sender");
            uiController.showMessage(sender, title, message);
        } else if (Action.START_EVENT.equals(action)) {
            String event_id = intent.getStringExtra("event_id");
            String event_name = intent.getStringExtra("event_name");
            activity.setToCurrentEvent(event_id, event_name);
            if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                bluetoothController.startDiscovery();
            } else if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE) {
                activity.registerReceiver(receiver, new StartBluetoothIntentFilter());
                receiver_registered = true;
                bluetoothController.setDiscoverable();
            } else {
                activity.registerReceiver(receiver, new StartBluetoothIntentFilter());
                receiver_registered = true;
                bluetoothController.turnOnBluetooth();
            }
        } else if (Action.STOP_EVENT.equals(action)) {

            String event_id = intent.getStringExtra("event_id");

            SharedPreferences sharedPref = activity.getSharedPreferences(
                    context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            String EVENT_ID = sharedPref.getString("EVENT_ID", "");

            if (event_id.equals(EVENT_ID)) {
                bluetoothController.stopDiscovery();
                bluetoothController.turnOffBluetooth();
            }
        }
    }

}