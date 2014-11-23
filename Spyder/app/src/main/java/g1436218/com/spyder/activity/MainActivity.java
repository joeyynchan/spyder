package g1436218.com.spyder.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.DisplayMacAddress;
import g1436218.com.spyder.asyncTask.FetchAttendeeList;
import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.fragment.BaseFragment;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.object.UserMap;
import g1436218.com.spyder.service.BluetoothDiscovery;


public class MainActivity extends BaseActivity {

    private Intent bluetoothDiscoveryIntent;
    private UserMap userMap;

    private TextView textview_attendee;
    private TextView textview_interaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.customOnCreate(savedInstanceState, R.layout.activity_main);

        /*Display Device Information */
        new DisplayMacAddress(this).execute();
        new FetchAttendeeList(this).execute();

    }

    @Override
    protected void onStart() {

        /* Start BluetoothDiscovery Service */
        bluetoothDiscoveryIntent = new Intent(getBaseContext(), BluetoothDiscovery.class);
        startService(bluetoothDiscoveryIntent);

        super.onStart();
    }

    @Override
    protected void onStop() {

        /* Stop BluetoothDiscovery Service */
        stopService(bluetoothDiscoveryIntent);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                finish();
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            // Do nothing
        } else {
            getFragmentManager().popBackStack();
            setTitle("Spyder");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_attendee: showAttendees(); break;
            case R.id.textview_interaction: showInteractions(); break;
            default: break;
        }
    }

    @Override
    public void initializeView() {
        textview_attendee = (TextView) findViewById(R.id.textview_attendee);
        textview_attendee.setOnClickListener(this);
        textview_interaction = (TextView) findViewById(R.id.textview_interaction);
        textview_interaction.setOnClickListener(this);
    }

    private void showInteractions() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof InteractionFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new InteractionFragment(), "CURRENT_FRAGMENT");
            getFragmentManager().popBackStack();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void showAttendees() {
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof AttendeeFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            //fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.fragment_container, new AttendeeFragment(), "CURRENT_FRAGMENT");
            getFragmentManager().popBackStack();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
