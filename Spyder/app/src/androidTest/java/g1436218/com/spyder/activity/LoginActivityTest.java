package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.widget.Button;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.fragment.LoginFragment;
import g1436218.com.spyder.fragment.RegisterFragment;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity activity;
    private Button button_signup;
    private Button button_login;

    private Instrumentation.ActivityMonitor mainActivityMonitor;
    private int TIMEOUT_IN_MS = 100;
    private static String TAG = "LoginTest";

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = (LoginActivity) getActivity();
        button_login = (Button) activity.findViewById(R.id.button_activity_login_login);
        button_signup = (Button) activity.findViewById(R.id.button_activity_login_signUp);
    }

    @SmallTest
    public void testLoginButtonLayout() {
        String expected = "Log in";
        String actual = button_login.getText().toString();
        assertEquals(expected, actual);
    }

    @SmallTest
    public void testSignUpButtonLayout() {
        String expected = "Sign Up";
        String actual = button_signup.getText().toString();
        assertEquals(expected, actual);
    }

    @SmallTest
    public void testLoginButtonClick() {
        TouchUtils.clickView(this, button_login);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertTrue(fragment instanceof LoginFragment);
    }

    @SmallTest
    public void testSignUpButtonClick() {
        TouchUtils.clickView(this, button_signup);
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertTrue(fragment instanceof RegisterFragment);
    }
/*
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

    }*/


    @Override
    protected void tearDown() throws Exception{
        Log.d(TAG, "tearDown");
        super.tearDown();


    }
}
