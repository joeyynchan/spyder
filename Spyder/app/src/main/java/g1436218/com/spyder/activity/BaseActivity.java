package g1436218.com.spyder.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
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

    /* Retreive String with tag from SharedPreference */
    public String getSharedPrefString(String tag) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String result = sharedPref.getString(tag, "");
        return result;
    }

    /* Put String with tag into SharedPreference */
    public void putSharedPrefString(String tag, String value) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, value);
        editor.commit();
    }

    public boolean getSharedPrefBoolean(String tag) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean result = sharedPref.getBoolean(tag, false);
        return result;
    }

    public void putSharedPrefBoolean(String tag, boolean bool) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(tag, bool);
        editor.commit();
    }

    public void clearSharedPref() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }


    public abstract void onClick(View v);
    public abstract void initializeView();


}
