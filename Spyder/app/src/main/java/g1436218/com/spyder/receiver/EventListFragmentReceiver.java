package g1436218.com.spyder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import g1436218.com.spyder.fragment.EventListFragment;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.object.Event;

/**
 * Created by Joey on 02/12/2014.
 */
public class EventListFragmentReceiver extends BroadcastReceiver {

    EventListFragment fragment;

    public EventListFragmentReceiver(EventListFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Action.EVENT_ADAPTER_ADD_ITEM.equals(action)) {
            Bundle extras = intent.getExtras();
            String status, event_id, event_name;
            status = extras.getString("status");
            event_id = extras.getString("event_id");
            event_name = extras.getString("event_name");
            fragment.addItem(status, new Event(event_name, event_id, status, null, null, null, null));
        } else if (Action.EVENT_ADAPTER_CLEAR.equals(action)) {
            fragment.clearAdapter();
        }
    }


}
