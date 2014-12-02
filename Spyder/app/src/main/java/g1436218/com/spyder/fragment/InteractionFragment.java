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
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Attendee;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.receiver.InteractionFragmentReceiver;
import g1436218.com.spyder.service.BluetoothDiscovery;

public class InteractionFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener {

    private final String TITLE = "Ongoing Interactions";

    private InteractionAdapter adapter;
    private IntentFilter intentFilter;

    public InteractionFragment(MainActivity activity) {
        super(activity, R.layout.fragment_interaction);
    }

    @Override
    protected void initializeView() {
        getActivity().setTitle(TITLE);
        /* Initialize Listview */
        this.adapter = new InteractionAdapter(getActivity(), R.layout.listview_interaction);
        ListView listview_interactions = (ListView) getActivity().findViewById(R.id.listview_interaction_list);
        listview_interactions.setAdapter(adapter);
        listview_interactions.setOnItemClickListener(this);
        adapter.addAllToAdapter(activity.getClone());
    }

    @Override
    protected void registerReceiver() {
        receiver = new InteractionFragmentReceiver(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(Action.UPDATE_INTERACTION_FRAGMENT_ADAPTER);
        activity.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Interaction item = (Interaction) parent.getItemAtPosition(position);
        new FetchUserProfile(activity, item.getUsername()).execute();
    }

    public void addAllToAdapter() {
        adapter.addAllToAdapter(activity.getClone());
    }

}
