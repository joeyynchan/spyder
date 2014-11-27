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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.InteractionAdapter;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.service.BluetoothDiscovery;

public class InteractionFragment extends BaseMainFragment implements AdapterView.OnItemClickListener {

    private final String TITLE = "Ongoing Interactions";

    private InteractionAdapter adapter;
    private UIUpdateReceiver receiver;
    private IntentFilter intentFilter;

    public InteractionFragment(MainActivity activity) {
        super(activity, R.layout.fragment_interaction);
    }

    @Override
    protected void initializeView() {

        getActivity().setTitle(TITLE);

        /* Initialize BroadcastReceiver */
        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDiscovery.UPDATE_ADAPTER);
        this.receiver = new UIUpdateReceiver(this);
        activity.registerReceiver(receiver, intentFilter);

        /* Initialize Listview */
        this.adapter = new InteractionAdapter(getActivity(), R.layout.listview_interaction);
        ListView listview_interactions = (ListView) getActivity().findViewById(R.id.listview_interaction_list);
        listview_interactions.setAdapter(adapter);
        listview_interactions.setOnItemClickListener(this);
        adapter.addAllToAdapter(activity.getInteractions());
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Interaction item = (Interaction) parent.getItemAtPosition(position);
        new FetchUserProfile(activity, item.getUsername()).execute();
    }

    private class UIUpdateReceiver extends BroadcastReceiver {

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
