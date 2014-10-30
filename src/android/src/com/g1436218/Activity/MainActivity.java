package com.g1436218.Activity;

import g1436218.spyder.R;

import java.util.HashSet;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.g1436218.Object.Connection;

public class MainActivity extends Activity {
	
	private final int TASK_DELAY_DURATION = 30; /* in seconds */
	private final String TAG = "MainActivity";
	
	private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
	private HashSet<Connection> connections;
	private Handler handler = new Handler();
	
	private final BroadcastReceiver receiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {

        	TextView textview = (TextView) findViewById(R.id.textView1);
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
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializeIntentFilter();
		
		Button button = (Button) findViewById(R.id.button1);
		if (!BTAdapter.isEnabled()) {
			button.setText("Bluetooth is not enabled");
		}
        button.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
            	handler.post(mDiscoveryTask);
            	Log.d(TAG, "Handler has posted mDiscoveryTask");
            }
        });
        
	}
	
	private void initializeIntentFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(receiver, filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
