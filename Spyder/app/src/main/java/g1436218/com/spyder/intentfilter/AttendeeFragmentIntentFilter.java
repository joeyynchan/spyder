package g1436218.com.spyder.intentfilter;

import android.content.IntentFilter;

import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.object.Action;

/**
 * Created by ync12 on 14/1/15.
 */
public class AttendeeFragmentIntentFilter extends IntentFilter {

    public AttendeeFragmentIntentFilter() {
        addAction(Action.UPDATE_ATTENDEE_FRAGMENT_ADAPTER);
    }
}
