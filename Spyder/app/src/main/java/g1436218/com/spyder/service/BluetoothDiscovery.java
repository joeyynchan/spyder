package g1436218.com.spyder.service;

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

import java.util.HashSet;

import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.UserMap;

public class BluetoothDiscovery extends Service {

    public static final String DEVICE_DETECTED = "DEVICE_DETECTED";
    public static final String RESET_LIST = "RESET_LIST";

    private final String TAG = "BluetoothDiscovery";

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    private HashSet<Interaction> connections;
    private Handler handler = new Handler();
    private UserMap userMap;

    private final BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                int strength = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                
                if (userMap.containsKey(device.getAddress())) {
                    String username = userMap.get(device.getAddress());
                    connections.add(new Interaction(username, strength));
                    broadcastDeviceDetected(username, strength);
                    Log.d(TAG, connections.toString());
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.d(TAG, "ACTION_DISCOVERY_STARTED");
		        /* Reset the list of connections */
                connections = new HashSet<Interaction>();

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d(TAG, "ACTION_DISCOVERY_FINISHED: " + connections.toString());
                new SubmitBluetoothData(connections).execute();
                broadcastResetList();
            }
        }

        private void broadcastDeviceDetected(String username, int strength) {
            Intent intent = new Intent();
            intent.setAction(DEVICE_DETECTED);
            intent.putExtra("USERNAME", username);
            intent.putExtra("STRENGTH", strength);
            sendBroadcast(intent);
        }

        private void broadcastResetList() {
            Intent intent = new Intent();
            intent.setAction(RESET_LIST);
            sendBroadcast(intent);
        }
    };

    private Runnable mDiscoveryTask = new Runnable() {

        @Override
        public void run() {
            BTAdapter.startDiscovery();
            handler.postDelayed(this, GlobalConfiguration.BLUETOOTH_TIME_INTERVAL * 1000);
        }
    };

    private void initializeIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        connections = new HashSet<Interaction>();

		/* Enable Bluetooth */
        if (!BTAdapter.isEnabled()) {
            BTAdapter.enable();
        }

        userMap = UserMap.getInstance();
        initializeIntentFilter();

        handler.post(mDiscoveryTask);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        handler.removeCallbacks(mDiscoveryTask);
    }
}
