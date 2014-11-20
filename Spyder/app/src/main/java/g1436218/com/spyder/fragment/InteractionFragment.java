package g1436218.com.spyder.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.service.BluetoothDiscovery;

public class InteractionFragment extends BaseFragment {

    private UIUpdateReceiver receiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interaction, container, false);

    }


    @Override
    public void onResume()  {
        /* Register UIUpdateReceiver */
        receiver = new UIUpdateReceiver(this.getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDiscovery.DEVICE_DETECTED);
        intentFilter.addAction(BluetoothDiscovery.RESET_LIST);
        this.getActivity().registerReceiver(receiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        /* Unregister Receiver */
        this.getActivity().unregisterReceiver(receiver);
    }

    private class UIUpdateReceiver extends BroadcastReceiver {

        Activity activity;

        public UIUpdateReceiver(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = (TextView) activity.findViewById(R.id.textview_interaction_result);
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
