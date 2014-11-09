package g1436218.com.spyder.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayMacAddress;
import g1436218.com.spyder.asyncTask.FetchAttendeeList;
import g1436218.com.spyder.asyncTask.UpdateUserMap;
import g1436218.com.spyder.backbone.JSONParser;
import g1436218.com.spyder.backbone.QueryExecutor;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends BaseActivity {

    private UIUpdateReceiver receiver;
    private Intent bluetoothDiscoveryIntent;
    private UserMap userMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.customOnCreate(savedInstanceState, R.layout.activity_main);

        /*Display Device Information */
        new DisplayMacAddress(this).execute();
        new FetchAttendeeList(this).execute();

    }

    @Override
    protected void onStart() {

        /* Register UIUpdateReceiver */
        receiver = new UIUpdateReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDiscovery.DEVICE_DETECTED);
        intentFilter.addAction(BluetoothDiscovery.RESET_LIST);
        registerReceiver(receiver, intentFilter);

        /* Start BluetoothDiscovery Service */
        bluetoothDiscoveryIntent = new Intent(getBaseContext(), BluetoothDiscovery.class);
        startService(bluetoothDiscoveryIntent);

        super.onStart();
    }

    @Override
    protected void onStop() {
        /* Unregister Receiver */
        unregisterReceiver(receiver);

        /* Stop BluetoothDiscovery Service */
        stopService(bluetoothDiscoveryIntent);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initializeView() {
    }

    private class UIUpdateReceiver extends BroadcastReceiver {

        Activity activity;

        public UIUpdateReceiver(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = (TextView) findViewById(R.id.textView2);
            String action = intent.getAction();
            if (BluetoothDiscovery.DEVICE_DETECTED.equals(action)) {
                String address = intent.getStringExtra("MAC_ADDRESS");
                textView.setText(textView.getText() + "\n" + address);
            } else if (BluetoothDiscovery.RESET_LIST.equals(action)) {
                textView.setText("Results:");
            }
        }
    }
}
