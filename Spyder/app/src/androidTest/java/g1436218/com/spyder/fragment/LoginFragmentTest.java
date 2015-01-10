package g1436218.com.spyder.fragment;

import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.UnlinkDevice;
import g1436218.com.spyder.fragment.LoginFragment;

public class LoginFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity activity;
    private LoginFragment fragment;

    private EditText username, password;
    private TextView message;
    private Button login;

    public LoginFragmentTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() {
        activity = (LoginActivity) getActivity();
        Button login_button = (Button) activity.findViewById(R.id.button_activity_login_login);
        TouchUtils.clickView(this, login_button);
        this.fragment = (LoginFragment) activity.getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        assertNotNull(fragment);
        username = (EditText) activity.findViewById(R.id.edittext_fragment_login_username);
        password = (EditText) activity.findViewById(R.id.edittext_fragment_login_password);
        message = (TextView) activity.findViewById(R.id.textview_fragment_login_errmsg);
        login = (Button) activity.findViewById(R.id.button_fragment_login_attemptLogin);
    }

    @SmallTest
    public void testUsernameEditTextLayout() {
        assertNotNull("Username EditText is null", username);
        String expected = "Username";
        String actual = username.getHint().toString();
        assertEquals(expected, actual);
    }

    @SmallTest
    public void testPasswordEditTextLayout() {
        assertNotNull("Password EditText is null", password);
        String expected = "Password";
        String actual = password.getHint().toString();
        assertEquals(expected, actual);
    }

    @SmallTest
    public void testMessageTextViewLayout() {
        assertNotNull("ErrorMessage TextView is null", message);
        String expected = "";
        String actual = message.getText().toString();
        assertEquals(expected, actual);
    }

    @SmallTest
    public void testLoginButtonLayout() {
        assertNotNull("Login Button is null", login);
        String expected = "Log In";
        String actual = login.getText().toString();
        assertEquals(expected, actual);
    }

    @LargeTest
    public void testLoginWithWrongInfo() {
        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync("demo3");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password);
        getInstrumentation().sendStringSync("demo3");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, login);
        assertEquals("Username/Password not match\n", message.getText().toString());
    }

    @LargeTest
    public void testLoginWithEmptyUsername() {
        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password);
        getInstrumentation().sendStringSync("demo3");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, login);
        assertEquals("Username should not be empty", message.getText().toString());
    }

    @LargeTest
    public void testLoginWithEmptyPassword() {
        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync("demo003");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password);
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, login);
        assertEquals("Password should not be empty", message.getText().toString());
    }

    @LargeTest
    public void testLoginWithCorrectInfo() {

        Instrumentation.ActivityMonitor mainActivityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync("demo005");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password);
        getInstrumentation().sendStringSync("1");
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, login);
        MainActivity mainActivity = (MainActivity)
                mainActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", mainActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, mainActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                MainActivity.class, mainActivity.getClass());
        getInstrumentation().removeMonitor(mainActivityMonitor);

        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(context.getString(R.string.username), "");
        String password = sharedPref.getString(context.getString(R.string.password), "");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        new UnlinkDevice(mainActivity, username, password).execute();
        mainActivity.finish();


    }
}
