package g1436218.com.spyder.fragment;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.BaseActivity;
import g1436218.com.spyder.activity.LoginActivity;

public class RegisterFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity activity;

    private EditText username, password1, password2;
    private Button register;
    private TextView errorMessage;

    public RegisterFragmentTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    private void gotoRegisterFragment() {
        Button signup = (Button) activity.findViewById(R.id.button_activity_login_signUp);
        TouchUtils.clickView(this, signup);
        getInstrumentation().waitForIdleSync();
        /*Fragment fragment = activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertNotNull("Fragment is null", fragment);
        assertEquals("Fragment is not of Register Fragment", RegisterFragment.class, fragment.getClass());*/
    }

    private void initializeView() {
        username = (EditText) activity.findViewById(R.id.edittext_fragment_register_username);
        password1 = (EditText) activity.findViewById(R.id.edittext_fragment_register_password1);
        password2 = (EditText) activity.findViewById(R.id.edittext_fragment_register_password2);
        errorMessage = (TextView) activity.findViewById(R.id.textview_fragment_register_errmsg);
        register = (Button) activity.findViewById(R.id.button_fragment_register_register);
    }

    @SmallTest
    public void testLayout() {

        gotoRegisterFragment();
        initializeView();

        assertNotNull("Username EditText is null", username);
        String expected = "Username";
        String actual = username.getHint().toString();
        assertEquals(expected, actual);

        assertNotNull("Password1 EditText is null", password1);
        expected = "Create Your Password";
        actual = password1.getHint().toString();
        assertEquals(expected, actual);

        assertNotNull("Password2 EditText is null", password2);
        expected = "Confirm Your Password";
        actual = password2.getHint().toString();
        assertEquals(expected, actual);

        assertNotNull("ErrorMessage TextView is null", errorMessage);
        expected = "";
        actual = errorMessage.getText().toString();
        assertEquals(expected, actual);

        assertNotNull("Register Button is null", register);
        expected = "Register";
        actual = register.getText().toString();
        assertEquals(expected, actual);
    }

    /*
    @LargeTest
    public void testRegistration() throws Throwable{
        _testRegisterWithDifferentPassword();
        _testRegisterWithEmptyPassword();
        _testRegisterWithEmptyUsername();
        _testRegisterWithRepeatingUsername();
    }*/

    @LargeTest
    public void testRegisterWithEmptyUsername() throws Throwable {

        final String _username = "";
        final String _password = "1";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        gotoRegisterFragment();
        initializeView();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText(_username);
                password1.setText(_password);
                password2.setText(_password);
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Username cannot be empty", errorMessage.getText().toString());
    }

    @LargeTest
    public void testRegisterWithEmptyPassword() throws Throwable {

        final String _username = "demo999";
        final String _password = "";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        gotoRegisterFragment();
        initializeView();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText(_username);
                password1.setText(_password);
                password2.setText(_password);
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Password cannot be empty", errorMessage.getText().toString());
    }

    @LargeTest
   public void testRegisterWithRepeatingUsername() throws Throwable {

        final String _username = "demo001";
        final String _password = "demo001";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        gotoRegisterFragment();
        initializeView();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText(_username);
                password1.setText(_password);
                password2.setText(_password);
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Username is taken", errorMessage.getText().toString());
    }

    @LargeTest
    public void testRegisterWithDifferentPassword() throws Throwable {

        final String _username = "demo4";
        final String _password = "whatever";

        final CountDownLatch latch = new CountDownLatch(1);
        activity.setListener(new BaseActivity.IJobListener() {
            @Override
            public void executionDone() {
                latch.countDown();
            }
        });

        gotoRegisterFragment();
        initializeView();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText(_username);
                password1.setText(_password);
                password2.setText(_password+"!");
                register.performClick();
            }
        });

        boolean await = latch.await(10, TimeUnit.SECONDS);
        assertTrue(await);

        assertEquals("Passwords do not match", errorMessage.getText().toString());
    }


}
