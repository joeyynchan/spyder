package g1436218.com.spyder.fragment;

import android.bluetooth.BluetoothAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.SharedPref;

/**
 * Created by Joey on 11/01/2015.
 */
public class EventListFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public EventListFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        BluetoothAdapter.getDefaultAdapter().disable();
        assertNotNull("MainActivity is null", activity);
        assertEquals("activity is not MainActivity.class", MainActivity.class, activity.getClass());
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_event_list);
        TouchUtils.clickView(this, button);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        //activity.clearSharedPref();
    }

    public void testListViewNotNull() {
        ListView listview = (ListView) activity.findViewById(R.id.listview_eventlist);
        assertNotNull(listview);
    }

    public void testSwipeRefreshLayoutNotNull() {
        SwipeRefreshLayout listview = (SwipeRefreshLayout) activity.findViewById(R.id.swiperefreshlayout_eventlist);
        assertNotNull(listview);
    }

    public void testSearchView() {
        SearchView searchview = (SearchView) activity.findViewById(R.id.searchview_eventlist);
        assertNotNull(searchview);
    }

    public void testNoEvent() {
        String expected = "Event List";
        String actual = activity.getTitle().toString();
        assertEquals(expected, actual);
    }

    public void testWithEvent() {
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_interactions);
        TouchUtils.clickView(this, button);

        activity.putSharedPrefString(SharedPref.EVENT_ID, "dummy id");
        activity.putSharedPrefString(SharedPref.EVENT_NAME, "TestWithEvent");

        button = (LinearLayout) activity.findViewById(R.id.button_event_list);
        TouchUtils.clickView(this, button);

        String expected = "TestWithEvent";
        String actual = activity.getTitle().toString();
        assertEquals(expected, actual);
    }
}
