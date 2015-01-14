package g1436218.com.spyder.object;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.service.BluetoothDiscovery;

public class BluetoothController {

    MainActivity activity;
    UIController uiController;

    private int nid = 0;
    private boolean discovery = false;
    private Intent bluetoothDiscoveryIntent;

    public BluetoothController(MainActivity activity) {
        this.activity = activity;
        this.uiController = activity.getUIController();
    }

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    /* Start discovery mode
     * Change the status light
     */
    public void startDiscovery() {
        if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            bluetoothDiscoveryIntent = new Intent(activity.getBaseContext(), BluetoothDiscovery.class);
            activity.startService(bluetoothDiscoveryIntent);
            uiController.setStatus(1);
            discovery = true;
        }
    }

    /* Stop discovery mode
     * Change the status light
     * And it notifies the interaction fragment to clear the list
     */
    public void stopDiscovery() {
        if (discovery) {
            activity.stopService(bluetoothDiscoveryIntent);
            uiController.setStatus(BluetoothAdapter.getDefaultAdapter().getScanMode());
            Intent intent = new Intent();
            intent.setAction(Action.CLEAR_INTERACTION_FRAGMENT_ADAPTER);
            activity.sendBroadcast(intent);
            discovery = false;
        }
    }

    /* Turn on bluetooth */
    public void turnOnBluetooth() {
        BluetoothAdapter.getDefaultAdapter().enable();
    }

    /* Ask User Permission to set device Bluetooth discoverable */
    public void setDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        activity.startActivity(discoverableIntent);
    }

    /* Turn off the Bluetooth
     * If it is in discovery mode, Turn it off as well
     */
    public void turnOffBluetooth() {
        BluetoothAdapter.getDefaultAdapter().disable();
        if (discovery) {
            stopDiscovery();
        }
    }

    public boolean isDiscovery() {
        return discovery;
    }

}
