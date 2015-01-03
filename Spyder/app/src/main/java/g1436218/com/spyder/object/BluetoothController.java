package g1436218.com.spyder.object;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

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

    public void startDiscovery() {
        if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            bluetoothDiscoveryIntent = new Intent(activity.getBaseContext(), BluetoothDiscovery.class);
            activity.startService(bluetoothDiscoveryIntent);
            uiController.setStatus(1);
            discovery = true;
        }
    }

    public void stopDiscovery() {
        activity.stopService(bluetoothDiscoveryIntent);
        uiController.setStatus(BluetoothAdapter.getDefaultAdapter().getScanMode());
        discovery = false;
    }

    public void turnOnBluetooth() {
        BluetoothAdapter.getDefaultAdapter().enable();
    }

    public void setDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        activity.startActivity(discoverableIntent);
    }

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
