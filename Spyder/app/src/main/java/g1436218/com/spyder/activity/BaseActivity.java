package g1436218.com.spyder.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.CheckConnection;
import g1436218.com.spyder.config.GlobalConfiguration;

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    private IJobListener listener;

    public static interface IJobListener {
        void executionDone();
    }

    public void setListener(IJobListener listener) {
        this.listener = listener;
    }

    public IJobListener getListener() {
        return listener;
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        new CheckConnection(this).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public abstract void onClick(View v);
    public abstract void initializeView();


}
