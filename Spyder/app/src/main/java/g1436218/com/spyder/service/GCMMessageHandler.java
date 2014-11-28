package g1436218.com.spyder.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.receiver.GCMBroadcastReceiver;

public class GCMMessageHandler extends IntentService {

    private final String LAUNCH_APPLICATION = "LAUNCH_APPLICATION";

    public static final String START_DISCOVERY = "START_DISCOVERY";
    public static final String STOP_DISCOVERY = "STOP_DISCOVERY";
    public static final String FETCH_ATTENDEES = "FETCH_ATTENDEES";

    String title, message;
    private Handler handler;

    public GCMMessageHandler() {
        super("GCMMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        title = message = "";
        handler = new Handler();
    }

    /**
     * This method is triggered when GCM service sends an Intent to this
     * device. Intent stores everything in the GCM message, you are free
     * to extract the content from the intent and improvise to do anything.
     *
     * Example includes toast, notification and vibration
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        String action = extras.getString("action");

        if (LAUNCH_APPLICATION.equals(action)) {
            launchApplication();
        } else if (START_DISCOVERY.equals(action)) {
            startDiscovery();
        } else if (STOP_DISCOVERY.equals(action)) {
            stopDiscovery();
        } else if (FETCH_ATTENDEES.equals(action)) {
            fetchAttendees();
        }

        Log.i("GCM", "Received : (" + messageType + ") \n" + "Action: " + action);

        GCMBroadcastReceiver.completeWakefulIntent(intent);

    }

    public void vibrate(int duration) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

    private void launchApplication() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

    private void startDiscovery() {
        launchApplication();
        Intent intent = new Intent();
        intent.setAction(START_DISCOVERY);
        sendBroadcast(intent);
    }

    private void stopDiscovery() {
        launchApplication();
        Intent intent = new Intent();
        intent.setAction(STOP_DISCOVERY);
        sendBroadcast(intent);
    }

    private void fetchAttendees() {
        launchApplication();
        Intent intent = new Intent();
        intent.setAction(FETCH_ATTENDEES);
        sendBroadcast(intent);
    }

}