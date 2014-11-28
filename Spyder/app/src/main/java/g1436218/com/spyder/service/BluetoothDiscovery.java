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
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.UserMap;

public class BluetoothDiscovery extends Service {


    private final String TAG = "BluetoothDiscovery";

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    private Handler handler = new Handler();
    private UserMap userMap;
    private int count = 1;

    private final BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                int strength = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                
                if (userMap.containsKey(device.getAddress())) {
                    String username = userMap.get(device.getAddress());
                    broadcastDeviceDetected(username, strength);
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.i ("BluetoothDiscovery", "START_DISCOVERY");

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Log.i ("BluetoothDiscovery", "FINISH_DISCOVERY");
                broadcastResetList();
            }
        }

        private void broadcastDeviceDetected(String username, int strength) {
            Intent intent = new Intent();
            intent.setAction(Action.DEVICE_DETECTED);
            intent.putExtra("USERNAME", username);
            intent.putExtra("STRENGTH", strength);
            sendBroadcast(intent);
        }

        private void broadcastResetList() {
            Intent intent = new Intent();
            intent.setAction(Action.RESET_LIST);
            sendBroadcast(intent);
            if (count++ % GlobalConfiguration.NUMBER_OF_MINI_BATCHES == 0) {
                count = 1;
                broadcastSendData();
            }
        }

        private void broadcastSendData() {
            Intent intent = new Intent();
            intent.setAction(Action.SEND_DATA);
            sendBroadcast(intent);
        }
    };

    private Runnable mDiscoveryTask = new Runnable() {

        @Override
        public void run() {
            if (BTAdapter.isDiscovering()) {
                BTAdapter.cancelDiscovery();
            }
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
