package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.app.Instrumentation;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.LinearLayout;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.adapter.InteractionAdapter;

public class InteractionFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public InteractionFragmentTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        BluetoothAdapter.getDefaultAdapter().disable();
        assertNotNull("MainActivity is null", activity);
        assertEquals("activity is not MainActivity.class", MainActivity.class, activity.getClass());
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.button_interactions);
        TouchUtils.clickView(this, button);
    }

    public void testAdapterNotNulll() {
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertEquals("Unexpected class: ", InteractionFragment.class, fragment.getClass());
        InteractionAdapter adapter = ((InteractionFragment)fragment).getAdapter();
        assertNotNull(adapter);
    }

}
