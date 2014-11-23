package g1436218.com.spyder.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.adapter.InteractionAdapter;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.service.BluetoothDiscovery;

public class InteractionFragment extends BaseFragment {

    private UIUpdateReceiver receiver;
    private InteractionAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new InteractionAdapter(getActivity(), R.layout.listview_interaction);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interaction, container, false);
    }

    @Override
    public void onResume()  {
        getActivity().setTitle("Ongoing Interactions");

        /* Register UIUpdateReceiver */
        receiver = new UIUpdateReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDiscovery.DEVICE_DETECTED);
        intentFilter.addAction(BluetoothDiscovery.RESET_LIST);
        this.getActivity().registerReceiver(receiver, intentFilter);

        /* Initialize Listview */
        ListView listview_interactions = (ListView) getActivity().findViewById(R.id.listview_interaction_list);
        listview_interactions.setAdapter(adapter);

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        /* Unregister Receiver */
        this.getActivity().unregisterReceiver(receiver);
    }

    private class UIUpdateReceiver extends BroadcastReceiver {

        Fragment fragment;

        public UIUpdateReceiver(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = (TextView) fragment.getActivity().findViewById(R.id.textview_interaction_result);
            String action = intent.getAction();
            if (BluetoothDiscovery.DEVICE_DETECTED.equals(action)) {
                String username = intent.getStringExtra("USERNAME");
                int strength = intent.getIntExtra("STRENGTH", 0);
                adapter.addToList(new Interaction(username, strength));
            } else if (BluetoothDiscovery.RESET_LIST.equals(action)) {
                adapter.addAllToAdapter();
            }
        }
    }

}
