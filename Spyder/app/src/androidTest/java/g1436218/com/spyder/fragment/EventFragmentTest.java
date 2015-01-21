package g1436218.com.spyder.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Instrumentation;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.SharedPref;
import g1436218.com.spyder.object.Event;
import g1436218.com.spyder.util.SwipeListView;

public class EventFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    private Button joinEvent, setToCurrentEvent;

    public EventFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        BluetoothAdapter.getDefaultAdapter().disable();
        assertNotNull("MainActivity is null", activity);
    }

    /* Start the EventFragment with a non-current + non-attending event
     * Join Event button should be visible but set to current Event should be goine
     */
    public void testNotCurrentEvent() {
        Event event = new Event("Event", "Event", "Nothing", null, null, null, null);
        EventFragment eventFragment = new EventFragment(activity, event);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(new IntentFilter(), null, false);
        getInstrumentation().addMonitor(monitor);
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        joinEvent = (Button) activity.findViewById(R.id.button_fragment_event_joinEvent);
        setToCurrentEvent = (Button) activity.findViewById(R.id.button_fragment_event_setToCurrentEvent);
        assertTrue(joinEvent.getVisibility() == View.VISIBLE);
        assertTrue(setToCurrentEvent.getVisibility() == View.GONE);
    }

    /* Start the EventFragment with a non-current + attending event
     * Join Event button should be visible but set to current Event should be goine
     */
    public void testNonCurrentButAttendingEvent() {
        Event event = new Event("Event", "Event", "Attending", null, null, null, null);
        EventFragment eventFragment = new EventFragment(activity, event);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(new IntentFilter(), null, false);
        getInstrumentation().addMonitor(monitor);
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        joinEvent = (Button) activity.findViewById(R.id.button_fragment_event_joinEvent);
        setToCurrentEvent = (Button) activity.findViewById(R.id.button_fragment_event_setToCurrentEvent);
        assertTrue(joinEvent.getVisibility() == View.GONE);
        assertTrue(setToCurrentEvent.getVisibility() == View.VISIBLE);
        assertTrue(setToCurrentEvent.isClickable());
    }

    /* Start the EventFragment with a non-current + attending event
     * Join Event button should be visible but set to current Event should be goine
     */
    public void testCurrentEvent() {
        activity.putSharedPrefString(SharedPref.EVENT_ID, "Event");
        activity.putSharedPrefString(SharedPref.EVENT_NAME, "Event");
        Event event = new Event("Event", "Event", "Attending", null, null, null, null);
        EventFragment eventFragment = new EventFragment(activity, event);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment, "CURRENT_FRAGMENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(new IntentFilter(), null, false);
        getInstrumentation().addMonitor(monitor);
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        joinEvent = (Button) activity.findViewById(R.id.button_fragment_event_joinEvent);
        setToCurrentEvent = (Button) activity.findViewById(R.id.button_fragment_event_setToCurrentEvent);
        assertTrue(joinEvent.getVisibility() == View.GONE);
        assertTrue(setToCurrentEvent.getVisibility() == View.VISIBLE);
        assertTrue(!setToCurrentEvent.isClickable());
        activity.clearSharedPref();
    }


}