package g1436218.com.spyder.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.object.Action;

public class InteractionFragmentReceiver extends BroadcastReceiver {

    InteractionFragment fragment;

    public InteractionFragmentReceiver(InteractionFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Action.UPDATE_INTERACTION_FRAGMENT_ADAPTER.equals(action)) {
            //Log.i("UPDATE_INTERACTION_FRAGMENT_ADAPTER", ((MainActivity) fragment.getActivity()).getInteractionPackage().getClone().toString());
            //Log.i("interactionsAfterClear", activity.getInteractions().toString());

            fragment.updateAdapter();

        } if (Action.CLEAR_INTERACTION_FRAGMENT_ADAPTER.equals(action)) {
            fragment.clearAdapter();
        }
    }

}