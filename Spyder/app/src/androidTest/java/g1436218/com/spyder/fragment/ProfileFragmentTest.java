package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.app.Instrumentation;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.content.IntentFilter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.SharedPref;

public class ProfileFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public ProfileFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        BluetoothAdapter.getDefaultAdapter().disable();
        assertNotNull("MainActivity is null", activity);
        assertEquals("activity is not MainActivity.class", MainActivity.class, activity.getClass());
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_profile);
        TouchUtils.clickView(this, button);
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(new IntentFilter(), null, false);
        getInstrumentation().addMonitor(monitor);
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
    }

    public void testBackButton() {
        sendKeys(KeyEvent.KEYCODE_BACK);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertEquals(ProfileFragment.class, fragment.getClass());
    }

    public void testEditProfile() {
        activity.putSharedPrefString(SharedPref.USERNAME, "demo009");
        getInstrumentation().invokeMenuActionSync(activity, R.id.action_edit, 0);
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(new IntentFilter(), null, false);
        getInstrumentation().addMonitor(monitor);
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertEquals(EditProfileFragment.class, fragment.getClass());
    }


}
