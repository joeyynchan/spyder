package g1436218.com.spyder.intentfilter;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;

import g1436218.com.spyder.object.Action;

public class MainActivityIntentFilter extends IntentFilter {

    public MainActivityIntentFilter() {
        addAction(Action.DEVICE_DETECTED);
        addAction(Action.RESET_LIST);
        addAction(Action.SEND_DATA);
        addAction(Action.START_DISCOVERY);
        addAction(Action.STOP_DISCOVERY);
        addAction(Action.START_BLUETOOTH);
        addAction(Action.STOP_BLUETOOTH);
        addAction(Action.FETCH_ATTENDEES);
        addAction(Action.GET_GCM);
        addAction(Action.UPDATE_CURRENT_EVENT);
        addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    }
}
