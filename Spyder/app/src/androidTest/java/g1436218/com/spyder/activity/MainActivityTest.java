package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.MenuItem;
import android.widget.LinearLayout;

import g1436218.com.spyder.R;
import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.fragment.AttendeeFragmentTest;
import g1436218.com.spyder.fragment.EventListFragment;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.fragment.ProfileFragment;

/**
 * Created by Joey on 10/01/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        BluetoothAdapter.getDefaultAdapter().disable();
        assertNotNull("MainActivity is null", activity);
        assertEquals("activity is not MainActivity.class", MainActivity.class, activity.getClass());
    }

    @SmallTest
    public void testReceiverrNotNull() {
        assertNotNull("MainActivityReceiver is null", activity.getReceiver());
    }

    @SmallTest
    public void testUIControllerNotNull() {
        assertNotNull("UIController is null", activity.getUIController());
    }

    @SmallTest
    public void testBluetoothControllerNotNull() {
        assertNotNull("BluetoothController is null", activity.getBluetoothController());
    }

    @SmallTest
    public void testInteractionPackageNotNull() {
        assertNotNull("InteractionPackage is null", activity.getInteractionPackage());
    }

    @SmallTest
    public void testAttendeesNotNull() {
        assertNotNull("Attendees is null", activity.getAttendees());
    }

    /* UICONTROLLER */

    @SmallTest
    public void testInteractionsButton() {
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_interactions);
        TouchUtils.clickView(this, button);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertNotNull("Fragment is null", fragment);
        assertEquals("Fragment is no InteractionFragment Class", InteractionFragment.class, fragment.getClass());
    }

    @SmallTest
    public void testProfileButton() {
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_profile);
        TouchUtils.clickView(this, button);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertNotNull("Fragment is null", fragment);
        assertEquals("Fragment is no ProfileFragment Class", ProfileFragment.class, fragment.getClass());
    }

    @SmallTest
    public void testAttendeesButton() {
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_attendee_list);
        TouchUtils.clickView(this, button);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertNotNull("Fragment is null", fragment);
        assertEquals("Fragment is no InteractionFragment Class", AttendeeFragment.class, fragment.getClass());
    }

    @SmallTest
    public void testEventListButton() {
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_event_list);
        TouchUtils.clickView(this, button);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertNotNull("Fragment is null", fragment);
        assertEquals("Fragment is no EventListFragment Class", EventListFragment.class, fragment.getClass());
    }

    @SmallTest
    public void testOptionMenu() {
        getInstrumentation().invokeMenuActionSync(activity, R.menu.menu_main, 0);
        assertTrue("Bluetooth should not be On!", !BluetoothAdapter.getDefaultAdapter().isEnabled());
        MenuItem item = (MenuItem) activity.findViewById(R.id.action_start_bluetooth);

    }

}
