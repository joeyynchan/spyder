package g1436218.com.spyder.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
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

    public MainActivityReceiver(MainActivity activity) {
        this.activity = activity;
        this.interactionPackage = activity.getInteractionPackage();
        this.bluetoothController = activity.getBluetoothController();
        this.uiController = activity.getUIController();
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
        }
    }

}