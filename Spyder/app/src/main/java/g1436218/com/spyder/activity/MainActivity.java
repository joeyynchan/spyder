package g1436218.com.spyder.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayMacAddress;
import g1436218.com.spyder.asyncTask.FetchAttendeeList;
import g1436218.com.spyder.fragment.BaseFragment;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends BaseActivity {

    private UIUpdateReceiver receiver;
    private Intent bluetoothDiscoveryIntent;
    private UserMap userMap;

    private TextView textview_show_attendee;

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
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            // Do nothing
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_show_attendee: showAttendee(); break;
            default: break;
        }
    }

    @Override
    public void initializeView() {
        textview_show_attendee = (TextView) findViewById(R.id.textview_show_attendee);
        textview_show_attendee.setOnClickListener(this);
    }

    private void showAttendee() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new BaseFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
                String username = intent.getStringExtra("USERNAME");
                String rssi = intent.getStringExtra("RSSI");
                textView.setText(textView.getText() + "\n" + username + " - " + rssi);
            } else if (BluetoothDiscovery.RESET_LIST.equals(action)) {
                textView.setText("Results:");
            }
        }
    }
}
