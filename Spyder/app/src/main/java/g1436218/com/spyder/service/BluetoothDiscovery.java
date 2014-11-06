package g1436218.com.spyder.service;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.util.HashSet;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.Connection;

public class BluetoothDiscovery extends Service {

    private final int TASK_DELAY_DURATION = 15; /* in seconds */
    private final String TAG = "BackgroundDiscovery";

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    private HashSet<Connection> connections;
    private Handler handler = new Handler();

    private final BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                connections.add(new Connection(device.getAddress(), rssi));
                Log.d(TAG, connections.toString());
                //Log.d(TAG, "Device, " + device.getName() + " (" + device.getAddress() + ") has been detected with rssi: " + rssi + " dBm.");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.d(TAG, "ACTION_DISCOVERY_STARTED");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d(TAG, "ACTION_DISCOVERY_FINISHED");
                showResult();
            }
        }
    };

    private Runnable mDiscoveryTask = new Runnable() {
        @Override
        public void run() {
            BTAdapter.startDiscovery();
            handler.postDelayed(this, TASK_DELAY_DURATION * 1000);
        }
    };

    private void initializeIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
    }

    private void showResult() {
        //TextView textview = (TextView) activity.findViewById(R.id.textView1);
        //textview.setText(connections.toString());
        Log.d(TAG, connections.toString());
		/* Reset the list of connections */
        connections = new HashSet<Connection>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        connections = new HashSet<Connection>();

		/* Enable Bluetooth */
        if (!BTAdapter.isEnabled()) {
            BTAdapter.enable();
        }

        initializeIntentFilter();

        handler.post(mDiscoveryTask);
        Log.d(TAG, "Handler has posted mDiscoveryTask");
        //activity.unregisterReceiver(receiver);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
