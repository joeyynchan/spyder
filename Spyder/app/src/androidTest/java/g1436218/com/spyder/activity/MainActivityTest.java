package g1436218.com.spyder.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.android.internal.util.Predicate;

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
        assertNotNull("MainActivity is null", activity);
        assertEquals("activity is not MainActivity.class", MainActivity.class, activity.getClass());
    }

    @SmallTest
    public void testTrue() {
        assertTrue(true);
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

}
