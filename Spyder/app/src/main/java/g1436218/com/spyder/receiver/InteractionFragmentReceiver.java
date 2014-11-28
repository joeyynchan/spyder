package g1436218.com.spyder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.service.BluetoothDiscovery;

public class InteractionFragmentReceiver extends BroadcastReceiver {

    InteractionFragment fragment;

    public InteractionFragmentReceiver(InteractionFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDiscovery.UPDATE_ADAPTER.equals(action)) {
            //Log.i("UPDATE_ADAPTER", activity.getClone().toString());
            //Log.i("interactionsAfterClear", activity.getInteractions().toString());
            fragment.addAllToAdapter();
        }
    }

}