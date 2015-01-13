package g1436218.com.spyder.intentfilter;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.util.Log;

import g1436218.com.spyder.receiver.StartBluetoothReceiver;

/**
 * Created by ync12 on 13/1/15.
 */
public class StartBluetoothIntentFilter extends IntentFilter {

    public StartBluetoothIntentFilter() {
        Log.d("DEBUG", "created~");
        addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
    }
}
