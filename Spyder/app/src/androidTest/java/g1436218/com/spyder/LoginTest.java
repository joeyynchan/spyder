package g1436218.com.spyder;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;

/**
 * Created by Cherie on 11/23/2014.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity activity;
    private EditText editText_login_username;
    private EditText editText_login_password;
    private Button login_button_login;
    private Instrumentation.ActivityMonitor mainActivityMonitor;
    private int TIMEOUT_IN_MS = 100;
    private static String TAG = "LoginTest";

    public LoginTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        Log.d(TAG, "setup");
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
        editText_login_username = (EditText) activity.findViewById(R.id.edittext_activity_login_username);
        editText_login_password = (EditText) activity.findViewById(R.id.edittext_activity_login_password);
        login_button_login = (Button) activity.findViewById(R.id.button_activity_login_login);
    }

    public void testLoginToMainActivity() {
        Log.d(TAG, "testLoginToMainActivity");
        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                editText_login_username.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("demo3");
        getInstrumentation().waitForIdleSync();

        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                editText_login_password.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("test");
        getInstrumentation().waitForIdleSync();

        // Set up an ActivityMonitor
        mainActivityMonitor =
                getInstrumentation().addMonitor(MainActivity.class.getName(),
                        null, false);
        // Validate that ReceiverActivity is started
        TouchUtils.clickView(this, login_button_login);
        MainActivity mainActivity = (MainActivity)
                mainActivityMonitor.waitForActivityWithTimeout(TIMEOUT_IN_MS);
        assertNotNull("ReceiverActivity is null", mainActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, mainActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                MainActivity.class, mainActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(mainActivityMonitor);

    }


    @Override
    protected void tearDown() throws Exception{
        Log.d(TAG, "tearDown");
        super.tearDown();


    }
}
