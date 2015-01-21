package g1436218.com.spyder.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import g1436218.com.spyder.R;
import g1436218.com.spyder.config.GlobalConfiguration;

/**
 * Created by Joey on 10/01/2015.
 */
public class MainActivityTest extends BaseMainActivityTest {

    @SmallTest
    public void testSharePreferences() {
        Context context = activity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(context.getString(R.string.username), "");
        String password = sharedPref.getString(context.getString(R.string.password), "");
        assertEquals("Username in SharedPreferences is incorrect", username, _username);
        assertEquals("Password in SharedPreferences is incorrect", password, _password);
    }
}
