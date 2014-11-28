package g1436218.com.spyder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.FetchAttendee;
import g1436218.com.spyder.asyncTask.SubmitBluetoothData;
import g1436218.com.spyder.object.Interaction;
import g1436218.com.spyder.service.BluetoothDiscovery;
import g1436218.com.spyder.service.GCMMessageHandler;

public class MainActivityReceiver extends BroadcastReceiver {

    MainActivity activity;

    public MainActivityReceiver(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDiscovery.DEVICE_DETECTED.equals(action)) {
            String username = intent.getStringExtra("USERNAME");
            int strength = intent.getIntExtra("STRENGTH", 0);
            activity.addToInteractions(new Interaction(username, strength));
        } else if (BluetoothDiscovery.RESET_LIST.equals(action)) {
            //Log.i("interactions", interactionPackage.getInteractions().toString());
            activity.addInteractionsToPackage();
        } else if (BluetoothDiscovery.SEND_DATA.equals(action)) {
            if (!activity.isPackageEmpty()) {
                new SubmitBluetoothData(activity, activity.getInteractionPackage()).execute();
            } else {
                activity.clearArray();
            }
        } else if (GCMMessageHandler.START_DISCOVERY.equals(action)) {
            activity.startBluetoothDiscoveryService();
        } else if (GCMMessageHandler.STOP_DISCOVERY.equals(action)) {
            activity.stopBluetoothDiscoveryService();
        } else if (GCMMessageHandler.FETCH_ATTENDEES.equals(action)) {
            new FetchAttendee(activity).execute();
        }
    }
}