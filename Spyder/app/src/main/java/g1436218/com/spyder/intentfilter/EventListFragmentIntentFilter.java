package g1436218.com.spyder.intentfilter;

import android.content.IntentFilter;

import g1436218.com.spyder.object.Action;

public class EventListFragmentIntentFilter extends IntentFilter{

    public EventListFragmentIntentFilter() {
        addAction(Action.EVENT_ADAPTER_ADD_ITEM);
        addAction(Action.EVENT_ADAPTER_CLEAR);
        addAction(Action.FETCH_EVENTS);
    }

}
