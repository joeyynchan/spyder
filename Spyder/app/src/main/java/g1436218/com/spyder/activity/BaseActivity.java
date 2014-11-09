package g1436218.com.spyder.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import g1436218.com.spyder.R;

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    protected void customOnCreate(Bundle savedInstanceState, int layoutResID) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        initializeView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onCreateSetup(Bundle savedInstanceState, int layoutResID) {

    }

    public abstract void onClick(View v);
    public abstract void initializeView();

}
