package g1436218.com.spyder.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.object.Action;

public class StartBluetoothReceiver extends BroadcastReceiver {

    private MainActivity activity;

    public StartBluetoothReceiver(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
            if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                Intent _intent = new Intent();
                _intent.setAction(Action.START_DISCOVERY);
                activity.sendBroadcast(_intent);
            }
        }
    }
}
