package g1436218.com.spyder.fragment;

import android.app.FragmentManager;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.InteractionAdapter;
import g1436218.com.spyder.asyncTask.DisplayAttendeeProfile;
import g1436218.com.spyder.dialogFragment.MessageFragment;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.SwipeDetector;
import g1436218.com.spyder.receiver.InteractionFragmentReceiver;

public class InteractionFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final String TITLE = "Interactions";

    private InteractionAdapter adapter;
    private IntentFilter intentFilter;
    private TextView textview_message;
    private InteractionPackage interactionPackage;
    private SwipeDetector swipeDetector;

    public InteractionFragment(MainActivity activity) {
        super(activity, R.layout.fragment_interaction);
        this.swipeDetector = new SwipeDetector();
        this.interactionPackage = activity.getInteractionPackage();
    }

    @Override
    protected void initializeView() {
        getActivity().setTitle(TITLE);

        textview_message = (TextView) activity.findViewById(R.id.textview_fragment_interaction_message);

        /* Initialize Listview */
        this.adapter = new InteractionAdapter(getActivity(), R.layout.listview_interaction);
        ListView listview_interactions = (ListView) getActivity().findViewById(R.id.listview_interaction_list);
        listview_interactions.setAdapter(adapter);
        listview_interactions.setOnTouchListener(swipeDetector);
        listview_interactions.setOnItemClickListener(this);
        updateAdapter();

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
        if (swipeDetector.swipeDetected()) {
            Button sendMessage = (Button) view.findViewById(R.id.button_listview_interaction_sendMessage);
            sendMessage.setOnClickListener(this);
            if (swipeDetector.getMovement().equals(SwipeDetector.Movement.RL)) {
                //sendMessage.setVisibility(View.VISIBLE);
                Log.d("Right to Left Movement Detected", "Right To Left");
                FragmentManager fragmentManager = activity.getFragmentManager();
                MessageFragment messageFragment = new MessageFragment(item.getName(), item.getGcm_id());
                messageFragment.show(getFragmentManager(), "Message");
            } else if (swipeDetector.getMovement().equals(SwipeDetector.Movement.LR)) {
                //sendMessage.setVisibility(View.GONE);
            }
        } else {
            new DisplayAttendeeProfile(activity, item.getUsername()).execute();
        }
    }

    /*
     * When the fragment is attached, extracts a copy of the current list of :interatcion,
     * sorts the list by strength, and updates the adapter
     */
    public void updateAdapter() {

        ArrayList<Interaction> clone = new ArrayList(interactionPackage.getClone());
        Collections.sort(clone, new SortByDescendingStrength());
        adapter.addAllToAdapter(clone);
        textview_message.setText(clone.isEmpty() ? "No Interactions Detected" : "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_listview_interaction_sendMessage: {

            }
        }
    }

    private class SortByDescendingStrength implements Comparator<Interaction> {
        @Override
        public int compare(Interaction lhs, Interaction rhs) {
            if (lhs.getStrength() < rhs.getStrength()) {
                return -1;
            } else if (lhs.getStrength() > rhs.getStrength()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
