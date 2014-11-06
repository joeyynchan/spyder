package g1436218.com.spyder.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayMacAddress;
import g1436218.com.spyder.asyncTask.UpdateUserMap;
import g1436218.com.spyder.backbone.JSONParser;
import g1436218.com.spyder.backbone.QueryExecutor;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends Activity {

    private UIUpdateReceiver receiver;
    private UserMap userMap;

    @Override
    protected void onStart() {
        receiver = new UIUpdateReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDiscovery.DEVICE_DETECTED);
        intentFilter.addAction(BluetoothDiscovery.RESET_LIST);
        registerReceiver(receiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }

        DisplayMacAddress.getInstance(this).execute();
        startService(new Intent(getBaseContext(), BluetoothDiscovery.class));

    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
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
