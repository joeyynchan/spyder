package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import junit.framework.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.BaseActivity;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.fragment.LoginFragment;
import g1436218.com.spyder.fragment.RegisterFragment;

/**
 * Created by Joey on 10/01/2015.
 */
public class RegisterFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity activity;

    private EditText username, password1, password2;
    private Button signup, register;
    private TextView errorMessage;

    public RegisterFragmentTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = (LoginActivity) getActivity();
        signup = (Button) activity.findViewById(R.id.button_activity_login_signUp);
        TouchUtils.clickView(this, signup);
        getInstrumentation().waitForIdleSync();
        Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertNotNull("Fragment is null", fragment);
        assertEquals("Fragment is not of Register Fragment", RegisterFragment.class, fragment.getClass());

        username = (EditText) activity.findViewById(R.id.edittext_fragment_register_username);
        assertNotNull("Username EditText is null", username);
        String expected = "Username";
        String actual = username.getHint().toString();
        assertEquals(expected, actual);

        password1 = (EditText) activity.findViewById(R.id.edittext_fragment_register_password1);
        assertNotNull("Password1 EditText is null", password1);
        expected = "Create Your Password";
        actual = password1.getHint().toString();
        assertEquals(expected, actual);

        password2 = (EditText) activity.findViewById(R.id.edittext_fragment_register_password2);
        assertNotNull("Password2 EditText is null", password2);
        expected = "Confirm Your Password";
        actual = password2.getHint().toString();
        assertEquals(expected, actual);

        errorMessage = (TextView) activity.findViewById(R.id.textview_fragment_register_errmsg);
        assertNotNull("ErrorMessage TextView is null", errorMessage);
        expected = "";
        actual = errorMessage.getText().toString();
        assertEquals(expected, actual);

        register = (Button) activity.findViewById(R.id.button_fragment_register_register);
        assertNotNull("Register Button is null", register);
        expected = "Register";
        actual = register.getText().toString();
        assertEquals(expected, actual);

    }

    private void initializeLatch() {

    }

    @LargeTest
    public void testRegisterWithEmptyUsername() throws Throwable {

        String _username = "";
        String _password = "1";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync(_username);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password1);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password2);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Username cannot be empty", errorMessage.getText().toString());
    }

    @LargeTest
    public void testRegisterWithEmptyPassword() throws Throwable {

        String _username = "demo999";
        String _password = "";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync(_username);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password1);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password2);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Password cannot be empty", errorMessage.getText().toString());
    }

    @LargeTest
    public void testRegisterWithRepeatingUsername() throws Throwable {

        String _username = "demo001";
        String _password = "demo001";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync(_username);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password1);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password2);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Username is taken", errorMessage.getText().toString());
    }

    /*
    @LargeTest
    public void testRegisterWithDifferentPassword() throws Throwable {

        String _username = "demo3";
        String _password = "whatever";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync(_username);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password1);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password2);
        getInstrumentation().sendStringSync(_password + "!");
        getInstrumentation().waitForIdleSync();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Passwords do not match", errorMessage.getText().toString());
    }*/

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }

}
