package g1436218.com.spyder.intentfilter;

import android.content.IntentFilter;

import g1436218.com.spyder.object.Action;

public class InteractionFragmentIntentFilter extends IntentFilter {

    public InteractionFragmentIntentFilter() {
        addAction(Action.CLEAR_INTERACTION_FRAGMENT_ADAPTER);
        addAction(Action.UPDATE_INTERACTION_FRAGMENT_ADAPTER);
    }

}
