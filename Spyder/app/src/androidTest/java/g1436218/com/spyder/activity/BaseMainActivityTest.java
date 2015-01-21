package g1436218.com.spyder.activity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.fragment.LoginFragment;

/**
 * Created by Joey on 10/01/2015.
 */
public class BaseMainActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    protected MainActivity activity;
    protected static final String _username = "demo005";
    protected static final String _password = "1";

    @Override
    protected void setUp() {

        Instrumentation.ActivityMonitor mainActivityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

        login();

        this.activity = (MainActivity)
                mainActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", this.activity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, mainActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                MainActivity.class, this.activity.getClass());
        getInstrumentation().removeMonitor(mainActivityMonitor);

    }

    protected void login() {
        LoginActivity activity = (LoginActivity) getActivity();
        Button login_button = (Button) activity.findViewById(R.id.button_activity_login_login);
        TouchUtils.clickView(this, login_button);
        EditText username = (EditText) activity.findViewById(R.id.edittext_fragment_login_username);
        EditText password = (EditText) activity.findViewById(R.id.edittext_fragment_login_password);
        Button login = (Button) activity.findViewById(R.id.button_fragment_login_attemptLogin);
        TouchUtils.clickView(this, username);
        getInstrumentation().sendStringSync(_username);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, password);
        getInstrumentation().sendStringSync(_password);
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, login);
    }

    public BaseMainActivityTest() {
        super(LoginActivity.class);
    }

}
