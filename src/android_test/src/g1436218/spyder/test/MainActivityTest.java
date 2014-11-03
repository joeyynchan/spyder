package g1436218.spyder.test;

import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import com.g1436218.Activity.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() {
		assertTrue(true);
	}

}
