package g1436218.com.spyder.fragment;

import android.app.Instrumentation;
import android.support.v4.widget.SwipeRefreshLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;

public class AttendeeFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public AttendeeFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        assertNotNull(activity);
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_attendee_list);
        TouchUtils.clickView(this, button);
    }

    public void testListView() {
        ListView listview = (ListView) activity.findViewById(R.id.listview_attendee);
        assertNotNull(listview);
    }

    public void testMessageTextView() {
        TextView textview = (TextView) activity.findViewById(R.id.textview_fragment_attendee_eventMsg);
        assertNotNull(textview);
    }

    public void testSwipRefreshLayout() {
        SwipeRefreshLayout layout = (SwipeRefreshLayout) activity.findViewById(R.id.swiperefreshlayout_attendee);
        assertNotNull(layout);
    }

    public void testRefresh() {
        TouchUtils.dragQuarterScreenDown(this, activity);
    }



}