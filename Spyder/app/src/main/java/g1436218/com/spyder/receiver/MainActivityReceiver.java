package g1436218.com.spyder.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Interaction;

public class MainActivityReceiver extends BroadcastReceiver {

    MainActivity activity;

    public MainActivityReceiver(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Action.DEVICE_DETECTED.equals(action)) {
            String username = intent.getStringExtra("USERNAME");
            int strength = intent.getIntExtra("STRENGTH", 0);
            activity.addToInteractions(new Interaction(username, strength));
        } else if (Action.RESET_LIST.equals(action)) {
            //Log.i("interactions", interactionPackage.getInteractions().toString());
            activity.addInteractionsToPackage();
        } else if (Action.SEND_DATA.equals(action)) {
            if (!activity.isPackageEmpty()) {
                new SubmitBluetoothData(activity, activity.getInteractionPackage()).execute();
            } else {
                activity.clearArray();
            }
        } else if (Action.START_DISCOVERY.equals(action)) {
            activity.startDiscovery();
        } else if (Action.STOP_DISCOVERY.equals(action)) {
            activity.stopDiscovery();
        } else if (Action.FETCH_ATTENDEES.equals(action)) {
            new FetchAttendees(activity).execute();
        } else if (Action.START_BLUETOOTH.equals(action)) {
            activity.turnOnBluetooth();
        } else if (Action.STOP_BLUETOOTH.equals(action)) {
            activity.turnOffBluetooth();
        } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
            if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                activity.setStatus(BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
            } else if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE) {
                activity.setStatus(BluetoothAdapter.SCAN_MODE_CONNECTABLE);
            } else {
                activity.setStatus(0);
            }
        }
    }
}