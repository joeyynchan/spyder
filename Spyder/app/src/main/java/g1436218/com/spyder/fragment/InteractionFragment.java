package g1436218.com.spyder.fragment;

import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.InteractionAdapter;
import g1436218.com.spyder.asyncTask.FetchUserProfile;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.object.InteractionPackage;
import g1436218.com.spyder.object.Interactions;
import g1436218.com.spyder.receiver.InteractionFragmentReceiver;

public class InteractionFragment extends BaseMainFragmentWithReceiver implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final String TITLE = "Interactions";

    private InteractionAdapter adapter;
    private IntentFilter intentFilter;
    private TextView textview_message;
    private InteractionPackage interactionPackage;
    private SwipeRefreshLayout swipeRefreshLayout;

    public InteractionFragment(MainActivity activity) {
        super(activity, R.layout.fragment_interaction);
        this.interactionPackage = activity.getInteractionPackage();

        //Testing
        /*
        this.interactionPackage = new InteractionPackage();
        this.interactionPackage.addInteraction(new Interaction("testing1", -90));
        this.interactionPackage.addInteraction(new Interaction("testing2", -50));
        this.interactionPackage.addInteraction(new Interaction("testing3", -70));
        this.interactionPackage.addInteraction(new Interaction("testing4", -80));
        this.interactionPackage.addInteraction(new Interaction("testing5", -90));
        this.interactionPackage.addInteraction(new Interaction("testing6", -40));
        this.interactionPackage.addInteraction(new Interaction("testing7", -20));
        this.interactionPackage.addInteraction(new Interaction("testing8", -10));
        this.interactionPackage.copyInteractionsToClone(); */
    }

    @Override
    protected void initializeView() {
        getActivity().setTitle(TITLE);

        textview_message = (TextView) activity.findViewById(R.id.textview_fragment_interaction_message);

        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swiperefreshlayout_interaction);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.white,
                android.R.color.white,
                android.R.color.white,
                android.R.color.white);

        /* Initialize Listview */
        this.adapter = new InteractionAdapter(getActivity(), R.layout.listview_interaction);
        ListView listview_interactions = (ListView) getActivity().findViewById(R.id.listview_interaction_list);
        listview_interactions.setAdapter(adapter);
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
        new FetchUserProfile(activity, item.getUsername()).execute();
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
    public void onRefresh() {
        this.interactionPackage.copyInteractionsToClone();
        updateAdapter();
        swipeRefreshLayout.setRefreshing(false);
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
