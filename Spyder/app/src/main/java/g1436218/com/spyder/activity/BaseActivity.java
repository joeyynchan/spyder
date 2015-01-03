package g1436218.com.spyder.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import g1436218.com.spyder.R;

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState, int layoutResID) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        initializeView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract void onClick(View v);
    public abstract void initializeView();

}
