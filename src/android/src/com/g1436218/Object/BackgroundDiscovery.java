package com.g1436218.Object;

import g1436218.spyder.R;

import java.util.HashSet;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class BackgroundDiscovery extends AsyncTask<Void, Void, Void>{

	private static BackgroundDiscovery instance;
	
	private final int TASK_DELAY_DURATION = 15; /* in seconds */
	private final String TAG = "BackgrounDiscovery";
	
	private Activity activity;
	private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
	private HashSet<Connection> connections;
	private Handler handler = new Handler();
	
	private final BroadcastReceiver receiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {

        	TextView textview = (TextView) activity.findViewById(R.id.textView1);
        	String action = intent.getAction();
	            
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                connections.add(new Connection(device.getAddress(), rssi));
                Log.d(TAG, "Device, " + device.getName() + " (" + device.getAddress() + ") has been detected with rssi: " + rssi + " dBm.");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
	            Log.d(TAG, "ACTION_DISCOVERY_STARTED");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	            textview.setText(connections.toString() + "\n");
	            Log.d(TAG, "ACTION_DISCOVERY_FINISHED");
	            Log.d(TAG, connections.toString());
            }
        }
    };
	
    private Runnable mDiscoveryTask = new Runnable() {
		@Override
		public void run() {
			/* Reset the list of connections */
        	connections = new HashSet<Connection>();
            BTAdapter.startDiscovery();
            handler.postDelayed(this, TASK_DELAY_DURATION * 1000);
		}
    };
    
    private BackgroundDiscovery(Activity activity){
    	this.activity = activity;
    }
    
    public static BackgroundDiscovery getInstance(Activity activity){
    	if (instance == null) {
    		instance = new BackgroundDiscovery(activity);
    	}
    	return instance;
    }
    
	@Override
	protected Void doInBackground(Void... params) {
		
		/* Enable Bluetooth */
		if (!BTAdapter.isEnabled()) {
			BTAdapter.enable();
		}
		
		initializeIntentFilter();

    	handler.post(mDiscoveryTask);
    	Log.d(TAG, "Handler has posted mDiscoveryTask");
		return null;
		
	}
	
	private void initializeIntentFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		activity.registerReceiver(receiver, filter);
	}

}
