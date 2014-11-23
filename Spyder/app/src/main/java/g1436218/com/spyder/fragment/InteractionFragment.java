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
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.InteractionAdapter;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.service.BluetoothDiscovery;

public class InteractionFragment extends BaseFragment {

    private final String TITLE = "Ongoing Interactions";

    private InteractionAdapter adapter;
    private UIUpdateReceiver receiver;
    private MainActivity activity;

    public InteractionFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new InteractionAdapter(getActivity(), R.layout.listview_interaction);
        this.receiver = new UIUpdateReceiver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interaction, container, false);
    }

    @Override
    public void onResume()  {
        getActivity().setTitle(TITLE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDiscovery.UPDATE_ADAPTER);
        getActivity().registerReceiver(receiver, intentFilter);

        /* Initialize Listview */
        ListView listview_interactions = (ListView) getActivity().findViewById(R.id.listview_interaction_list);
        listview_interactions.setAdapter(adapter);
        adapter.addAllToAdapter(activity.getInteractions());
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(receiver);
        super.onPause();
    }

    public class UIUpdateReceiver extends BroadcastReceiver {

        InteractionFragment fragment;
        MainActivity activity;

        public UIUpdateReceiver(InteractionFragment fragment) {
            this.fragment = fragment;
            this.activity = (MainActivity) fragment.getActivity();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDiscovery.UPDATE_ADAPTER.equals(action)) {
                adapter.addAllToAdapter(activity.getInteractions());
            }
        }
    }

}
